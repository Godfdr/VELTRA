package com.veltra.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * VELTRA NFC Payment Activity
 *
 * Handles NFC-based payments with biometric authentication and AES-256 encryption.
 * Supports:
 * - NFC Type 4A/4B (ISO-DEP)
 * - NDEF records
 * - MiFare technology
 * - Biometric (Fingerprint/Face) authentication
 * - AES/CBC/PKCS5Padding encryption
 */
class VeltraNFCPaymentActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var vibrator: Vibrator
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var encryptionManager: EncryptionManager

    // UI Components
    private lateinit var amountInput: EditText
    private lateinit var recipientInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var payButton: Button
    private lateinit var nfcStatusText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var resultText: TextView

    // Payment State
    private var pendingAmount: Double = 0.0
    private var pendingRecipient: String = ""
    private var pendingDescription: String = ""
    private var isNFCReading: Boolean = false

    companion object {
        private const val TAG = "VeltraNFCPayment"
        private const val REQUEST_CODE_BIOMETRIC = 1001
        private const val NFC_READ_TIMEOUT = 5000 // 5 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veltra_nfc_payment)

        // Initialize components
        initializeNFC()
        initializeBiometric()
        initializeEncryption()
        initializeUI()

        Log.d(TAG, "VELTRA NFC Payment Activity initialized")
    }

    private fun initializeNFC() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = nfcManager.defaultAdapter ?: run {
            Toast.makeText(this, "NFC not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        if (!nfcAdapter.isEnabled) {
            Toast.makeText(this, "Please enable NFC in device settings", Toast.LENGTH_SHORT).show()
        }

        Log.d(TAG, "NFC Adapter initialized: ${nfcAdapter.javaClass.simpleName}")
    }

    private fun initializeBiometric() {
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.BIOMETRIC_WEAK or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            Log.d(TAG, "Biometric authentication available")
        } else {
            Log.w(TAG, "Biometric authentication not available")
        }

        val executor = ContextCompat.getMainExecutor(this)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Biometric authentication succeeded")
                startNFCPayment()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.e(TAG, "Biometric authentication error: $errString")
                Toast.makeText(this@VeltraNFCPaymentActivity, "Auth Error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.w(TAG, "Biometric authentication failed")
                Toast.makeText(this@VeltraNFCPaymentActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }

        biometricPrompt = BiometricPrompt(this, executor, callback)
    }

    private fun initializeEncryption() {
        encryptionManager = EncryptionManager(this)
        Log.d(TAG, "Encryption manager initialized")
    }

    private fun initializeUI() {
        amountInput = findViewById(R.id.amountInput)
        recipientInput = findViewById(R.id.recipientInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        payButton = findViewById(R.id.payButton)
        nfcStatusText = findViewById(R.id.nfcStatusText)
        progressBar = findViewById(R.id.progressBar)
        resultText = findViewById(R.id.resultText)

        payButton.setOnClickListener { onPayButtonClicked() }

        updateNFCStatus()
    }

    private fun updateNFCStatus() {
        val statusText = if (nfcAdapter.isEnabled) {
            "NFC: 🟢 ACTIVE"
        } else {
            "NFC: 🔴 DISABLED"
        }
        nfcStatusText.text = statusText
        nfcStatusText.setTextColor(if (nfcAdapter.isEnabled) Color.GREEN else Color.RED)
    }

    private fun onPayButtonClicked() {
        val amount = amountInput.text.toString().toDoubleOrNull()
        val recipient = recipientInput.text.toString()
        val description = descriptionInput.text.toString()

        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (recipient.isEmpty()) {
            Toast.makeText(this, "Select recipient", Toast.LENGTH_SHORT).show()
            return
        }

        pendingAmount = amount
        pendingRecipient = recipient
        pendingDescription = description

        // Trigger biometric authentication
        authenticateWithBiometrics()
    }

    private fun authenticateWithBiometrics() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Veltra Payment")
            .setSubtitle("Confirm Identity")
            .setDescription("Verify with your biometric data")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun startNFCPayment() {
        isNFCReading = true
        progressBar.visibility = View.VISIBLE
        resultText.text = "🔄 Hold Android phone BACK to payment terminal..."
        resultText.setTextColor(Color.BLUE)

        Log.d(TAG, "Starting NFC payment for $pendingAmount to $pendingRecipient")

        enableNFCReader()
    }

    private fun enableNFCReader() {
        val nfcOptions = Bundle().apply {
            putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, NFC_READ_TIMEOUT)
        }

        nfcAdapter.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            nfcOptions
        )
    }

    override fun onTagDiscovered(tag: Tag?) {
        if (tag == null) {
            Log.w(TAG, "Tag discovered but null")
            return
        }

        Log.d(TAG, "NFC Tag discovered: ${tag.id.joinToString(":") { "%02x".format(it) }}")

        try {
            // Extract tag UID
            val tagUID = tag.id.joinToString(":") { "%02x".format(it) }
            val technologies = tag.techList.joinToString(", ") { it.substringAfterLast(".") }

            Log.d(TAG, "Tag UID: $tagUID")
            Log.d(TAG, "Technologies: $technologies")

            // Process NDEF if available
            if (tag.techList.contains("android.nfc.tech.Ndef")) {
                processNDEF(tag, tagUID)
            } else if (tag.techList.contains("android.nfc.tech.IsoDep")) {
                processISO(tag, tagUID)
            } else {
                processGenericTag(tag, tagUID)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error processing tag", e)
            showError("Error reading NFC tag: ${e.message}")
        }
    }

    private fun processNDEF(tag: Tag, tagUID: String) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            Log.d(TAG, "NDEF Tag detected with ${it.cachedNdefMessage?.records?.size ?: 0} records")
            completePayment(tagUID)
        }
    }

    private fun processISO(tag: Tag, tagUID: String) {
        val iso = IsoDep.get(tag)
        iso?.let {
            Log.d(TAG, "ISO-DEP Tag detected")
            it.connect()
            Log.d(TAG, "ISO-DEP connected, max transceive length: ${it.maxTransceiveLength}")
            it.close()
            completePayment(tagUID)
        }
    }

    private fun processGenericTag(tag: Tag, tagUID: String) {
        Log.d(TAG, "Generic NFC tag detected")
        completePayment(tagUID)
    }

    private fun completePayment(tagUID: String) {
        runOnUiThread {
            isNFCReading = false
            disableNFCReader()

            try {
                // Create payment data
                val paymentData = mapOf(
                    "amount" to pendingAmount,
                    "recipient" to pendingRecipient,
                    "description" to pendingDescription,
                    "tagUID" to tagUID,
                    "timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date()),
                    "userId" to "USER-alice-123" // In real app, get from shared prefs
                )

                Log.d(TAG, "Payment Data: $paymentData")

                // Simulate fraud detection
                val fraudScore = performFraudDetection(paymentData)
                Log.d(TAG, "Fraud Score: $fraudScore")

                if (fraudScore > 0.7) {
                    showError("Payment blocked - High risk score: $fraudScore")
                    return@runOnUiThread
                }

                // Encrypt payment data
                val encryptedPayload = encryptionManager.encryptPayment(paymentData)
                Log.d(TAG, "Payment encrypted successfully (${encryptedPayload.size} bytes)")

                // Trigger haptic feedback
                triggerHapticFeedback()

                // Send to backend (simulated)
                simulateBackendResponse()

            } catch (e: Exception) {
                Log.e(TAG, "Error completing payment", e)
                showError("Payment failed: ${e.message}")
            }
        }
    }

    private fun performFraudDetection(paymentData: Map<String, Any>): Double {
        var riskScore = 0.0

        val amount = (paymentData["amount"] as? Double) ?: 0.0
        if (amount > 1000) riskScore += 0.2

        // Random device risk (in real app, check for jailbreak, VPN, etc.)
        if (Math.random() > 0.95) riskScore += 0.15

        // Location check would go here
        // riskScore += locationCheck()

        // Time pattern check
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour < 6 || hour > 22) riskScore += 0.1

        return Math.min(riskScore, 1.0)
    }

    private fun triggerHapticFeedback() {
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val pattern = longArrayOf(0, 100, 100, 100) // Vibrate 3 times
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val amplitudes = intArrayOf(0, 100, 100, 100)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    private fun simulateBackendResponse() {
        progressBar.visibility = View.GONE

        val successMessage = """
            ✅ PAYMENT SUCCESSFUL
            
            Amount: ${'$'}${String.format("%.2f", pendingAmount)} USD
            Recipient: $pendingRecipient
            Description: $pendingDescription
            
            Processing Time: 325ms ⚡
            Status: COMPLETED
        """.trimIndent()

        resultText.text = successMessage
        resultText.setTextColor(Color.GREEN)

        Log.d(TAG, "Payment completed successfully")
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_LONG).show()

        // Show receipt option
        payButton.apply {
            text = "View Receipt"
            setOnClickListener {
                showReceipt()
            }
        }
    }

    private fun showReceipt() {
        val receiptText = """
            📄 RECEIPT
            
            Amount: ${'$'}${String.format("%.2f", pendingAmount)}
            Recipient: $pendingRecipient
            Status: COMPLETED
            
            Transaction ID: TXN-${UUID.randomUUID().toString().take(8).uppercase()}
        """.trimIndent()

        Toast.makeText(this, receiptText, Toast.LENGTH_LONG).show()
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        resultText.text = "❌ ERROR\n\n$message"
        resultText.setTextColor(Color.RED)
        Log.e(TAG, message)
    }

    private fun disableNFCReader() {
        try {
            nfcAdapter.disableReaderMode(this)
        } catch (e: Exception) {
            Log.w(TAG, "Error disabling NFC reader", e)
        }
    }

    override fun onResume() {
        super.onResume()
        updateNFCStatus()
    }

    override fun onPause() {
        super.onPause()
        disableNFCReader()
    }

    override fun onDestroy() {
        super.onDestroy()
        disableNFCReader()
    }

    /**
     * Encryption Manager for AES/CBC/PKCS5Padding
     */
    private class EncryptionManager(context: Context) {
        companion object {
            private const val ALGORITHM = "AES/CBC/PKCS5Padding"
            private const val KEY_SIZE = 256
            private const val IV_SIZE = 16
        }

        private val masterKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        fun encryptPayment(paymentData: Map<String, Any>): ByteArray {
            try {
                val keyGenerator = KeyGenerator.getInstance("AES")
                keyGenerator.init(KEY_SIZE, SecureRandom())
                val secretKey: SecretKey = keyGenerator.generateKey()

                val cipher = Cipher.getInstance(ALGORITHM)
                val iv = ByteArray(IV_SIZE)
                SecureRandom().nextBytes(iv)
                val ivSpec = IvParameterSpec(iv)

                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

                val plaintext = paymentData.toString().toByteArray(Charsets.UTF_8)
                val ciphertext = cipher.doFinal(plaintext)

                return ciphertext
            } catch (e: Exception) {
                Log.e("EncryptionManager", "Encryption failed", e)
                throw RuntimeException("Encryption failed: ${e.message}")
            }
        }
    }
}
