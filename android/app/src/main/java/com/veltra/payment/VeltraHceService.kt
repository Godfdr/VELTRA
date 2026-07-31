package com.veltra.payment

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log

/**
 * Veltra Host Card Emulation (HCE) Service
 * This allows the phone to "be the card" so another phone can read it for a transfer.
 */
class VeltraHceService : HostApduService() {

    companion object {
        private const val TAG = "VeltraHceService"
        
        // AID for Veltra P2P Transfer (must match apdu_service.xml)
        private const val VELTRA_AID = "F0010203040506"
        
        // Standard "Select AID" APDU Command
        private val SELECT_APDU_HEADER = byteArrayOf(0x00.toByte(), 0xA4.toByte(), 0x04.toByte(), 0x00.toByte())
        
        // Successful response code
        private val STATUS_SUCCESS = byteArrayOf(0x90.toByte(), 0x00.toByte())
        private val STATUS_FAILED = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    }

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu == null) return STATUS_FAILED
        
        val hexCommand = commandApdu.joinToString("") { "%02X".format(it) }
        Log.d(TAG, "Received APDU: $hexCommand")

        // Check if this is a "SELECT AID" command for Veltra
        if (isSelectVeltraAid(commandApdu)) {
            Log.d(TAG, "Veltra AID Selected! Returning account info...")
            
            // In a real run, you'd get the actual encrypted Veltra Tag from the Secure Repository
            // For now, we return a mock encrypted payload representing "@alexveltra"
            val responseData = "VLT-SECURE-PAYLOAD:alexveltra".toByteArray(Charsets.UTF_8)
            return responseData + STATUS_SUCCESS
        }

        return STATUS_FAILED
    }

    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "Deactivated: $reason")
    }

    private fun isSelectVeltraAid(commandApdu: ByteArray): Boolean {
        return commandApdu.size >= SELECT_APDU_HEADER.size &&
                commandApdu.sliceArray(0 until SELECT_APDU_HEADER.size).contentEquals(SELECT_APDU_HEADER)
    }
}
