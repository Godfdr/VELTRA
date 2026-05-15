package com.veltra.payment

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraMainBinding

class VeltraMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVeltraMainBinding
    private var isBalanceVisible = true
    private val actualBalance = "₦ 25,600.50"
    private val hiddenBalance = "₦ ••••••••"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initial theme application
        val sharedPref = getSharedPreferences("veltra_prefs", android.content.Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", true)
        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityVeltraMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupBalanceToggle()
        startBusAnimation()
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

    private fun setupDrawer() {
        // Drawer is not shown in high-fidelity image but keeping logic if user wants it
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_services -> showServicesBottomSheet()
                R.id.nav_logout -> {
                    startActivity(Intent(this, VeltraLoginActivity::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
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
            startActivity(Intent(this, VeltraTapAndPayActivity::class.java))
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
        
        // Profile Image Header - Strict following of UI/UX image
        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, VeltraProfileActivity::class.java))
        }

        // --- TEST RUN: Spot Me / Ping Simulation ---
        binding.notificationIcon.setOnClickListener {
            binding.pingRequestCard.visibility = View.VISIBLE
            binding.pingRequestCard.alpha = 0f
            binding.pingRequestCard.translationY = -50f
            binding.pingRequestCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .start()
        }

        binding.acceptPingBtn.setOnClickListener {
            binding.pingRequestCard.animate()
                .alpha(0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(300)
                .withEndAction { binding.pingRequestCard.visibility = View.GONE }
                .start()
            android.widget.Toast.makeText(this, "₦5,000 sent to Tola! ✅", android.widget.Toast.LENGTH_LONG).show()
        }

        binding.declinePingBtn.setOnClickListener {
            binding.pingRequestCard.visibility = View.GONE
        }
        
        // Bottom Navigation (Explicit IDs)
        binding.navHomeBtn.setOnClickListener { /* Already here */ }
        binding.navWalletBtn.setOnClickListener { startActivity(Intent(this, VeltraWalletsActivity::class.java)) }
        binding.navCardsBtn.setOnClickListener { startActivity(Intent(this, VeltraCardsActivity::class.java)) }
        binding.navProfileBtn.setOnClickListener { startActivity(Intent(this, VeltraProfileActivity::class.java)) }

        // Floating Center Pay Button
        binding.floatingPayBtn.setOnClickListener {
            startActivity(Intent(this, VeltraNFCPaymentActivity::class.java))
        }
    }
}
