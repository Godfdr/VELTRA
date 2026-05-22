package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraMainBinding

class VeltraMainActivity : VeltraBaseActivity() {

    private lateinit var binding: ActivityVeltraMainBinding
    private var isBalanceVisible = true
    private var isGhostModeActive = false
    
    // Using string resources for clean code
    private lateinit var actualBalance: String
    private lateinit var hiddenBalance: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityVeltraMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        actualBalance = getString(R.string.mock_balance)
        hiddenBalance = getString(R.string.mock_balance).replace(Regex("[0-9]"), "•")

        setupNavigation()
        setupBalanceToggle()
        setupGhostMode()
        startBusAnimation()
    }

    private fun setupGhostMode() {
        // Advanced 3-Finger Gesture for Ghost Mode
        binding.root.setOnTouchListener { v, event ->
            if (event.pointerCount == 3) {
                isGhostModeActive = true
                binding.mainContent.alpha = 0.05f
                android.widget.Toast.makeText(this, "Ghost Mode Active 👻", android.widget.Toast.LENGTH_SHORT).show()
            } else if (event.action == android.view.MotionEvent.ACTION_UP && isGhostModeActive) {
                // Keep it active until a specific action or just for a moment
                // For this demo, let's toggle it back on single tap elsewhere
            }
            false
        }

        binding.profileImage.setOnClickListener {
            if (isGhostModeActive) {
                isGhostModeActive = false
                binding.mainContent.alpha = 1.0f
                android.widget.Toast.makeText(this, "Ghost Mode Disabled", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, VeltraProfileActivity::class.java))
            }
        }
    }

    private fun startBusAnimation() {
        // Stationary Bus as requested
        binding.busLottie.setImageResource(R.drawable.ic_bus_modern)
        binding.busLottie.translationX = 0f 
    }

    private fun setupBalanceToggle() {
        binding.balanceVisibilityBtn.setOnClickListener {
            isBalanceVisible = !isBalanceVisible
            if (isBalanceVisible) {
                binding.balanceValue.text = actualBalance
                binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_view)
            } else {
                binding.balanceValue.text = hiddenBalance
                binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            }
        }
    }

    private fun showServicesBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_services_bottom_sheet, null)
        dialog.setContentView(view)
        
        view.findViewById<View>(R.id.service_agent_btn).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, VeltraAgentTopupActivity::class.java))
        }

        view.findViewById<View>(R.id.service_nfc_btn).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, VeltraContactlessActivity::class.java))
        }

        view.findViewById<View>(R.id.service_send_btn).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, VeltraSendMoneyStep1Activity::class.java))
        }

        view.findViewById<View>(R.id.service_receive_btn).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, VeltraReceiveActivity::class.java))
        }

        view.findViewById<View>(R.id.service_airtime_btn).setOnClickListener {
            dialog.dismiss()
            android.widget.Toast.makeText(this, "Airtime coming soon!", android.widget.Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.service_savings_btn).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, VeltraSavingsActivity::class.java))
        }
        
        dialog.show()
    }

    private fun setupNavigation() {
        // Quick Actions Grid
        
        // 1. Tap & Pay
        binding.quickActions.getChildAt(0).setOnClickListener {
            startActivity(Intent(this, VeltraTapAndPayActivity::class.java))
        }
        
        // 2. Send Money
        binding.quickActions.getChildAt(1).setOnClickListener {
            startActivity(Intent(this, VeltraSendMoneyStep1Activity::class.java))
        }
        
        // 3. Receive
        binding.quickActions.getChildAt(2).setOnClickListener {
            startActivity(Intent(this, VeltraReceiveActivity::class.java))
        }
        
        // 4. More Services
        binding.moreServicesBtn.setOnClickListener {
            showServicesBottomSheet()
        }

        // Wallet Card Section
        binding.walletCard.setOnClickListener {
            startActivity(Intent(this, VeltraWalletsActivity::class.java))
        }

        binding.fundWalletBtn.setOnClickListener {
            startActivity(Intent(this, VeltraTopUpMethodActivity::class.java))
        }
        
        // Profile Image Header
        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, VeltraProfileActivity::class.java))
        }

        // Recent Transactions Section Link to Luxury Analytics
        binding.transactionsLabel.setOnClickListener {
            startActivity(Intent(this, VeltraAnalyticsActivity::class.java))
        }

        // Simulation Card Actions
        binding.acceptPingBtn.setOnClickListener {
            binding.pingRequestCard.visibility = View.GONE
            android.widget.Toast.makeText(this, "₦5,000 sent to Tola! ✅", android.widget.Toast.LENGTH_LONG).show()
        }

        binding.declinePingBtn.setOnClickListener {
            binding.pingRequestCard.visibility = View.GONE
        }
        
        // Bottom Navigation
        binding.navHomeBtn.setOnClickListener { /* Already here */ }
        binding.navWalletBtn.setOnClickListener { startActivity(Intent(this, VeltraWalletsActivity::class.java)) }
        binding.navCardsBtn.setOnClickListener { startActivity(Intent(this, VeltraCardsActivity::class.java)) }
        binding.navProfileBtn.setOnClickListener { startActivity(Intent(this, VeltraProfileActivity::class.java)) }

        // Floating Center Pay Button - Instant Pay (Tap)
        binding.floatingPayBtn.setOnClickListener {
            startActivity(Intent(this, VeltraTapAndPayActivity::class.java))
        }

        // --- PREMIUM FEATURE: Instant Pay (Long Press + Biometric) ---
        binding.floatingPayBtn.setOnLongClickListener {
            triggerInstantPay()
            true
        }
    }

    private fun triggerInstantPay() {
        val executor = androidx.core.content.ContextCompat.getMainExecutor(this)
        val biometricPrompt = androidx.biometric.BiometricPrompt(this, executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val intent = Intent(this@VeltraMainActivity, VeltraTapAndPayActivity::class.java).apply {
                        putExtra("INSTANT_PAY", true)
                    }
                    startActivity(intent)
                }
            })

        val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.tap_to_pay))
            .setSubtitle(getString(R.string.confirm_identity))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
