package com.veltra.payment.offline

import java.security.Signature
import java.util.UUID

data class OfflineTapPayload(
    val txRef: String,
    val userId: String,
    val amount: Long,
    val counter: Long,
    val prevHash: String
)

/**
 * High-Speed Offline Signature Engine.
 * Generates cryptographically chained transaction receipts binding the 
 * hardware counter and previous block hash for absolute tamper resistance.
 */
object OfflineTransactionEngine {
    
    fun generateOfflineReceipt(userId: String, amount: Long, lastCounter: Long, prevHash: String): String {
        val nextCounter = lastCounter + 1
        val txRef = UUID.randomUUID().toString()
        
        // Context block string to bind cryptographically
        val dataToSign = "$txRef|$userId|$amount|$nextCounter|$prevHash"
        
        // Fetch hardware keys isolated inside the Secure Element
        val keyPair = SecureWalletHardware.getOrCreateHardwareKeyPair()
        
        // Initialize hardware signature calculation engine
        val s = Signature.getInstance("SHA256withECDSA")
        s.initSign(keyPair.private)
        s.update(dataToSign.toByteArray(Charsets.UTF_8))
        val signatureBytes = s.sign()
        
        val base64Signature = android.util.Base64.encodeToString(signatureBytes, android.util.Base64.NO_WRAP)
        
        // Build the production JSON packet to dispatch via NFC to the POS terminal
        return """
            {
                "tx_ref": "$txRef",
                "user_id": "$userId",
                "amount": $amount,
                "hardware_counter": $nextCounter,
                "device_sig": "$base64Signature",
                "prev_hash": "$prevHash"
            }
        """.trimIndent()
    }
}
