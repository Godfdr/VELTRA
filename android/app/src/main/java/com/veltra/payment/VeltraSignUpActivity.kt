package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.veltra.payment.databinding.ActivityVeltraSignupBinding

class VeltraSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraSignupBinding
    private var currentStep = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            if (currentStep > 1) goBackToStep1() else finish()
        }

        binding.continueBtn.setOnClickListener {
            when (currentStep) {
                1 -> validateAndGoToStep2()
                2 -> finalizeSignUp()
            }
        }

        binding.loginLink.setOnClickListener {
            startActivity(Intent(this, VeltraLoginActivity::class.java))
            finish()
        }
    }

    private fun validateAndGoToStep2() {
        val phone = binding.phoneInput.text.toString()
        if (phone.length < 10) {
            binding.phoneInput.error = "Enter a valid phone number"
            return
        }
        
        // Transition to Step 2
        currentStep = 2
        binding.step1Layout.visibility = View.GONE
        binding.step2Layout.visibility = View.VISIBLE
        binding.stepTitle.text = "Personal Info"
        binding.stepDesc.text = "Tell us a bit about yourself to verify your identity."
    }

    private fun goBackToStep1() {
        currentStep = 1
        binding.step1Layout.visibility = View.VISIBLE
        binding.step2Layout.visibility = View.GONE
        binding.stepTitle.text = "Join Veltra"
        binding.stepDesc.text = "Enter your mobile number to get started."
    }

    private fun finalizeSignUp() {
        val name = binding.nameInput.text.toString()
        if (name.isEmpty()) {
            binding.nameInput.error = "Name is required"
            return
        }

        // --- Biometric Opt-in Logic ---
        setupBiometrics()
    }

    private fun setupBiometrics() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricEnrollment()
            }
            else -> {
                // Biometrics not available, skip to main app
                completeAndNavigate()
            }
        }
    }

    private fun showBiometricEnrollment() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Biometrics linked! 🛡️", Toast.LENGTH_SHORT).show()
                    completeAndNavigate()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // If they cancel, we still let them in but without biometrics enabled
                    completeAndNavigate()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Secure Veltra")
            .setSubtitle("Link your biometrics for faster, safer payments")
            .setNegativeButtonText("Skip for now")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun completeAndNavigate() {
        Toast.makeText(this, "Welcome to Veltra, ${binding.nameInput.text}!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, VeltraMainActivity::class.java))
        finishAffinity() // Clear activity stack
    }
}
