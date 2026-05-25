package com.veltra.payment.offline

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore

/**
 * Isolated Hardware Key Management for Project VELTRA.
 * This object manages un-exportable private keys stored directly in the device's
 * StrongBox / Secure Element to prevent tampering by rooted OS environments.
 */
object SecureWalletHardware {
    private const val KEY_ALIAS = "VeltraOfflineMasterKey"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    fun getOrCreateHardwareKeyPair(): KeyPair {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        
        // If key already exists inside the Secure Element, return it
        if (keyStore.containsAlias(KEY_ALIAS)) {
            val privateKey = keyStore.getKey(KEY_ALIAS, null) as java.security.PrivateKey
            val publicKey = keyStore.getCertificate(KEY_ALIAS).publicKey
            return KeyPair(publicKey, privateKey)
        }

        // Otherwise, generate it inside isolated hardware
        val kpg = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC, ANDROID_KEYSTORE
        )
        
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).setDigests(KeyProperties.DIGEST_SHA256)
         .setUserAuthenticationRequired(false) // Allows instant NFC background taps
         .setIsStrongBoxBacked(true)           // Enforces physical StrongBox chip isolation
         .build()

        kpg.initialize(spec)
        return kpg.generateKeyPair()
    }
}
