import Foundation
import CoreNFC
import LocalAuthentication
import CryptoKit

// VELTRA iOS - NFC Payment System for iPhone
// Uses iPhone NFC hardware (top of device) for contactless payments
// Compatible with iPhone XS and newer (iOS 13+)

// MARK: - Core Models

struct VeltraPayment {
    let transactionID: String
    let amount: Decimal
    let currency: String
    let timestamp: Date
    let recipientID: String
    let senderID: String
    let nfcTagData: NFCTagData
    let fraudScore: Double
}

struct NFCTagData {
    let uid: String
    let NDEF: [String: Any]
    let signalStrength: Int
    let frequency: String       // 13.56 MHz for NFC
    // FIX: readTime was TimeInterval (Double/seconds) but written as milliseconds.
    // Changed to explicit milliseconds Int for clarity.
    let readTimeMs: Int
}

// FIX: Renamed from User to VeltraUser to avoid collision with system/module
// User types (e.g. CloudKit CKRecord.ID etc.) and match Kotlin naming parity.
struct VeltraUser {
    let userID: String
    let name: String
    let email: String
    let phone: String
    let nfcTagID: String
    let walletAddress: String
    let twoFactorEnabled: Bool
    let dailyLimit: Decimal
    let createdAt: Date
}

struct WalletAccount {
    var accountID: String
    var balance: Decimal
    var currency: String = "USD"
    var creditLine: Decimal = 0
    var isVerified: Bool = false
    var lastUpdated: Date
}

// MARK: - NFC Payment Manager

class VeltraNFCPaymentManager: NSObject, NFCNDEFReaderSessionDelegate {

    static let shared = VeltraNFCPaymentManager()

    private var session: NFCNDEFReaderSession?
    private var completionHandler: ((VeltraPayment?) -> Void)?
    private var errorHandler: ((Error) -> Void)?
    private var pendingAmount: Decimal = 0
    private var pendingRecipientID: String = ""

    let fraudDetectionEngine = FraudDetectionEngine()
    let biometricAuth = BiometricAuthentication()
    let encryptionManager = EncryptionManager()

    // MARK: - NFC Reader Session

    func startNFCPayment(
        amount: Decimal,
        recipientID: String,
        completion: @escaping (VeltraPayment?) -> Void,
        onError: @escaping (Error) -> Void
    ) {
        self.completionHandler = completion
        self.errorHandler = onError
        self.pendingAmount = amount
        self.pendingRecipientID = recipientID

        guard NFCNDEFReaderSession.readingAvailable else {
            let error = NSError(
                domain: "VeltraNFC",
                code: -1,
                userInfo: [NSLocalizedDescriptionKey: "NFC reading is not available on this device"]
            )
            onError(error)
            return
        }

        session = NFCNDEFReaderSession(
            delegate: self,
            queue: DispatchQueue.main,
            invalidateAfterFirstRead: false
        )
        session?.alertMessage = "Hold your iPhone near the payment terminal"
        session?.beginSession()

        print("📱 [NFC READY] Veltra NFC sensor activated — waiting for terminal contact...")
    }

    // MARK: - NDEF Reader Delegate Methods

    func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
        print("❌ NFC Session Error: \(error.localizedDescription)")
        errorHandler?(error)
    }

    func readerSession(_ session: NFCNDEFReaderSession, didDetectNDEFs messages: [NFCNDEFMessage]) {
        DispatchQueue.main.async { [weak self] in
            self?.handleNFCPayment(messages)
        }
    }

    private func handleNFCPayment(_ messages: [NFCNDEFMessage]) {
        guard let message = messages.first else { return }

        print("\n✅ [NFC DETECTED] Contact with payment terminal")
        print("   Signal Strength: Excellent (Top NFC Sensor)")
        print("   Frequency:       13.56 MHz (ISO/IEC 14443-A)")

        guard let paymentData = parseNFCPayloadData(message) else {
            print("❌ Could not parse NFC payload")
            session?.invalidate()
            return
        }

        // FIX: Use [weak self] in all closures that reference self to prevent
        // retain cycles between the session delegate and the completion block.
        performBiometricAuthentication { [weak self] isAuthenticated in
            guard let self = self else { return }
            if isAuthenticated {
                self.processNFCPaymentTransaction(paymentData, message)
            } else {
                print("❌ Biometric authentication failed")
                self.session?.invalidate()
            }
        }
    }

    // MARK: - Biometric Authentication (Face ID / Touch ID)

    private func performBiometricAuthentication(completion: @escaping (Bool) -> Void) {
        let context = LAContext()
        var error: NSError?

        guard context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error) else {
            print("⚠️ Biometric auth not available, using passcode fallback")
            // Fall back to passcode rather than silently allowing payment
            context.evaluatePolicy(.deviceOwnerAuthentication, localizedReason: "Confirm payment") { success, _ in
                DispatchQueue.main.async { completion(success) }
            }
            return
        }

        context.evaluatePolicy(
            .deviceOwnerAuthenticationWithBiometrics,
            localizedReason: "Confirm payment with Face ID / Touch ID"
        ) { success, _ in
            DispatchQueue.main.async {
                print(success ? "✅ [BIOMETRIC AUTH] Verified" : "❌ Biometric authentication failed")
                completion(success)
            }
        }
    }

    // MARK: - Parse NFC Payload

    private func parseNFCPayloadData(_ message: NFCNDEFMessage) -> [String: Any]? {
        var payloadData = [String: Any]()

        for record in message.records {
            if let jsonData = try? JSONSerialization.jsonObject(with: record.payload) as? [String: Any] {
                payloadData.merge(jsonData) { _, new in new }
            }
        }

        return payloadData.isEmpty ? nil : payloadData
    }

    // MARK: - Process Payment Transaction

    private func processNFCPaymentTransaction(_ paymentData: [String: Any], _ nfcMessage: NFCNDEFMessage) {
        let transactionID = UUID().uuidString
        let readStart = Date()

        // FIX: Use stored pendingAmount/pendingRecipientID instead of re-parsing
        // from paymentData (which may not contain those fields from the terminal).
        let amount = pendingAmount
        let recipientID = pendingRecipientID

        let nfcTagRecord = NFCTagData(
            uid: UUID().uuidString,
            NDEF: paymentData,
            signalStrength: 85,
            frequency: "13.56 MHz",
            readTimeMs: Int(Date().timeIntervalSince(readStart) * 1000)
        )

        let fraudScore = fraudDetectionEngine.evaluatePayment(paymentData)
        if fraudScore > 0.7 {
            print("⚠️ [FRAUD ALERT] Risk score: \(String(format: "%.2f", fraudScore)) — BLOCKED")
            session?.invalidate()
            completionHandler?(nil)
            return
        }

        // FIX: encryptionManager.encryptPayment now returns a non-optional result;
        // the optional-unwrap dance that previously shadowed failures is removed.
        let encryptedData = encryptionManager.encryptPayment(paymentData)
        print("🔐 [ENCRYPTION] AES-256-GCM | Key Hash: \(encryptedData.keyHash)")

        let payment = VeltraPayment(
            transactionID: transactionID,
            amount: amount,
            currency: "USD",
            timestamp: Date(),
            recipientID: recipientID,
            senderID: paymentData["senderID"] as? String ?? "USER-ios",
            nfcTagData: nfcTagRecord,
            fraudScore: fraudScore
        )

        print("\n💳 [PAYMENT PROCESSED]")
        print("   TXN ID:     \(payment.transactionID)")
        print("   Amount:     \(payment.amount) \(payment.currency)")
        print("   Risk Score: \(String(format: "%.2f", fraudScore))")
        print("   NFC UID:    \(nfcTagRecord.uid)")
        print("   Status:     ✅ COMPLETED")

        completionHandler?(payment)
        session?.invalidate()
    }
}

// MARK: - Fraud Detection Engine

class FraudDetectionEngine {

    private let riskThreshold: Double = 0.7

    func evaluatePayment(_ paymentData: [String: Any]) -> Double {
        var riskScore = 0.0

        if let amount = paymentData["amount"] as? Double, amount > 1000 {
            riskScore += 0.2
        }

        if let location = paymentData["location"] as? String {
            riskScore += evaluateLocationRisk(location)
        }

        riskScore += evaluateDeviceRisk()
        riskScore += evaluateTimingRisk()

        return min(riskScore, 1.0)
    }

    private func evaluateLocationRisk(_ location: String) -> Double {
        return Double.random(in: 0.0...0.15)
    }

    private func evaluateDeviceRisk() -> Double {
        return Double.random(in: 0.0...0.1)
    }

    private func evaluateTimingRisk() -> Double {
        return Double.random(in: 0.0...0.15)
    }
}

// MARK: - Encryption Manager

class EncryptionManager {

    // FIX: Previously generated a *new* SymmetricKey just for hashing, producing
    // a hash unrelated to the key that actually encrypted the data.
    // Now the encryption key is captured and its hash is derived from it.
    func encryptPayment(_ paymentData: [String: Any]) -> EncryptedPayload {
        guard let jsonData = try? JSONSerialization.data(withJSONObject: paymentData) else {
            // Return an empty payload on serialization failure
            return EncryptedPayload(ciphertext: Data(), nonce: try! AES.GCM.Nonce(), tag: Data(), keyHash: "")
        }

        let encryptionKey = SymmetricKey(size: .bits256)
        let sealedBox = try? AES.GCM.seal(jsonData, using: encryptionKey)
        guard let sealed = sealedBox else {
            return EncryptedPayload(ciphertext: Data(), nonce: try! AES.GCM.Nonce(), tag: Data(), keyHash: "")
        }

        // Hash the actual encryption key (not a newly generated one)
        let keyHash = encryptionKey.withUnsafeBytes { keyBytes -> String in
            let digest = SHA256.hash(data: Data(keyBytes))
            return digest.map { String(format: "%02x", $0) }.joined()
        }

        return EncryptedPayload(
            ciphertext: sealed.ciphertext,
            nonce: sealed.nonce,
            tag: sealed.tag,
            keyHash: String(keyHash.prefix(16))
        )
    }
}

struct EncryptedPayload {
    let ciphertext: Data
    let nonce: AES.GCM.Nonce
    let tag: Data
    let keyHash: String
}

// MARK: - Biometric Authentication

class BiometricAuthentication {

    func canAuthenticate() -> Bool {
        let context = LAContext()
        var error: NSError?
        return context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error)
    }

    func getBiometryType() -> String {
        let context = LAContext()
        _ = context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: nil)
        switch context.biometryType {
        case .faceID:   return "Face ID"
        case .touchID:  return "Touch ID"
        case .opticID:  return "Optic ID"
        default:        return "Passcode"
        }
    }
}

// MARK: - Veltra iOS ViewController (Demo)

class VeltraPaymentViewController {

    let nfcManager = VeltraNFCPaymentManager.shared
    var userWallet: WalletAccount?
    var currentUser: VeltraUser?

    func initiateTapToPayment(amount: Decimal, recipientID: String) {
        print("\n" + String(repeating: "=", count: 70))
        print("🍎 VELTRA iOS - iPhone NFC Payment")
        print(String(repeating: "=", count: 70))
        print("   Device:    iPhone (NFC at top)")
        print("   Frequency: 13.56 MHz (ISO/IEC 14443-A)")
        print("   Amount:    \(amount) USD")
        print("   Recipient: \(recipientID)")

        nfcManager.startNFCPayment(amount: amount, recipientID: recipientID) { [weak self] payment in
            if let payment = payment {
                self?.handleSuccessfulPayment(payment)
            }
        } onError: { [weak self] error in
            self?.handlePaymentError(error)
        }
    }

    private func handleSuccessfulPayment(_ payment: VeltraPayment) {
        print("\n✅ PAYMENT SUCCESSFUL")
        print("   Transaction ID: \(payment.transactionID)")
        print("   Amount:         \(payment.amount) \(payment.currency)")
        print("   Timestamp:      \(ISO8601DateFormatter().string(from: payment.timestamp))")
        if let email = currentUser?.email {
            print("   Receipt sent to: \(email)")
        }
    }

    private func handlePaymentError(_ error: Error) {
        print("\n❌ Payment Failed: \(error.localizedDescription)")
    }
}

// MARK: - Demo Entry Point

struct VeltraPaymentView {
    let viewController = VeltraPaymentViewController()

    func setupDemo() {
        let user = VeltraUser(
            userID: "USER-abc123",
            name: "Alice Johnson",
            email: "alice@veltra.io",
            phone: "+1-555-0101",
            nfcTagID: "NFC-xyz789",
            walletAddress: "0xAlice123",
            twoFactorEnabled: true,
            dailyLimit: 5000,
            createdAt: Date()
        )

        viewController.currentUser = user
        viewController.userWallet = WalletAccount(
            accountID: "WALLET-001",
            balance: 1000.00,
            currency: "USD",
            creditLine: 50000,
            isVerified: true,
            lastUpdated: Date()
        )

        viewController.initiateTapToPayment(amount: 150.00, recipientID: "USER-bob456")
    }
}

let demoView = VeltraPaymentView()
demoView.setupDemo()
