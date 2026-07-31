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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

/**
 * VELTRA NFC Payment Activity - UPDATED FOR P2P PHONE-TO-PHONE
 *
 * This activity can now READ another Veltra phone and initiate a transfer.
 */
class VeltraNFCPaymentActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var vibrator: Vibrator
    private lateinit var biometricPrompt: BiometricPrompt

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
        private const val VELTRA_AID = "F0010203040506"
        private val SELECT_APDU_COMMAND = byteArrayOf(
            0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte(), // CLA, INS, P1, P2
            0x07.toByte(), // Length (7 bytes for our AID)
            0xF0.toByte(), 0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x06.toByte() // AID
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veltra_nfc_payment)

        initializeNFC()
        initializeBiometric()
        initializeUI()

        Log.d(TAG, "VELTRA P2P NFC Payment Activity ready")
    }

    private fun initializeNFC() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = nfcManager.defaultAdapter ?: run {
            Toast.makeText(this, "NFC not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        if (!nfcAdapter.isEnabled) Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show()
    }

    private fun initializeBiometric() {
        val executor = ContextCompat.getMainExecutor(this)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startNFCPayment()
            }
        }
        biometricPrompt = BiometricPrompt(this, executor, callback)
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
        nfcStatusText.text = if (nfcAdapter.isEnabled) "NFC Ready: Tap phones back-to-back" else "NFC: Disabled"
        nfcStatusText.setTextColor(if (nfcAdapter.isEnabled) Color.GREEN else Color.RED)
    }

    private fun onPayButtonClicked() {
        val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
        if (amount <= 0) {
            Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
            return
        }
        pendingAmount = amount
        pendingRecipient = recipientInput.text.toString()
        pendingDescription = descriptionInput.text.toString()

        authenticateWithBiometrics()
    }

    private fun authenticateWithBiometrics() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Veltra P2P Transfer")
            .setSubtitle("Confirm Identity")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    private fun startNFCPayment() {
        isNFCReading = true
        progressBar.visibility = View.VISIBLE
        resultText.text = "🔄 Tap your phone to the RECEIVING phone..."
        resultText.setTextColor(Color.BLUE)

        nfcAdapter.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        if (isoDep != null) {
            try {
                isoDep.connect()
                Log.d(TAG, "Connected to Receiving Phone. Sending Select AID APDU...")
                
                // Send "Select Veltra AID" to the other phone's HCE Service
                val response = isoDep.transceive(SELECT_APDU_COMMAND)
                
                // Parse response
                val responseString = String(response.sliceArray(0 until response.size - 2), Charsets.UTF_8)
                val status = response.sliceArray(response.size - 2 until response.size)
                
                if (status[0] == 0x90.toByte() && status[1] == 0x00.toByte()) {
                    Log.d(TAG, "P2P Handshake Success! Recipient Data: $responseString")
                    
                    if (responseString.startsWith("VLT-SECURE-PAYLOAD:")) {
                        val recipientTag = responseString.removePrefix("VLT-SECURE-PAYLOAD:")
                        completeTransfer(recipientTag)
                    }
                }
                
                isoDep.close()
            } catch (e: Exception) {
                Log.e(TAG, "NFC P2P Error", e)
                runOnUiThread { showError("Could not detect Veltra on the other phone.") }
            }
        }
    }

    private fun completeTransfer(recipientTag: String) {
        runOnUiThread {
            isNFCReading = false
            nfcAdapter.disableReaderMode(this)
            progressBar.visibility = View.GONE
            
            pendingRecipient = recipientTag
            
            val successMsg = """
                ✅ P2P TRANSFER SUCCESSFUL
                
                Sent to: @$recipientTag
                Amount: ₦${String.format("%,.2f", pendingAmount)}
                
                Tap back-to-back transfer complete ⚡
            """.trimIndent()
            
            resultText.text = successMsg
            resultText.setTextColor(Color.GREEN)
            triggerHapticFeedback()
            Toast.makeText(this, "Money sent to @$recipientTag!", Toast.LENGTH_LONG).show()
        }
    }

    private fun triggerHapticFeedback() {
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        resultText.text = "❌ $message"
        resultText.setTextColor(Color.RED)
    }
}
