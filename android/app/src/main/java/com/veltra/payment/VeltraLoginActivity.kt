package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.veltra.payment.databinding.ActivityVeltraLoginBinding

class VeltraLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val phone = binding.phoneInput.text.toString()
            val pin = binding.pinInput.text.toString()

            if (phone.isNotEmpty() && pin.isNotEmpty()) {
                com.veltra.payment.network.ApiClient.post("/auth/login", mapOf("phone" to phone, "pin" to pin)) { success, _ ->
                    if (success) {
                        startActivity(Intent(this, VeltraMainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter login details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpLink.setOnClickListener {
            startActivity(Intent(this, VeltraSignUpActivity::class.java))
        }

        binding.biometricLoginBtn.setOnClickListener {
            showBiometricPrompt()
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@VeltraLoginActivity, VeltraMainActivity::class.java))
                    finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Veltra Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use PIN instead")
            .build()

        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            Toast.makeText(this, "Biometrics not available", Toast.LENGTH_SHORT).show()
        }
    }
}
