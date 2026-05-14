package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraMainBinding

class VeltraMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVeltraMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        // Quick Actions Grid
        val quickActions = binding.quickActions
        
        // 1. Tap & Pay
        (quickActions.getChildAt(0) as? ViewGroup)?.setOnClickListener {
            startActivity(Intent(this, VeltraTapAndPayActivity::class.java))
        }
        
        // 2. Send Money
        (quickActions.getChildAt(1) as? ViewGroup)?.setOnClickListener {
            startActivity(Intent(this, VeltraSendMoneyStep1Activity::class.java))
        }
        
        // 3. Receive
        (quickActions.getChildAt(2) as? ViewGroup)?.setOnClickListener {
            startActivity(Intent(this, VeltraReceiveActivity::class.java))
        }
        
        // 4. Pay Bills
        (quickActions.getChildAt(3) as? ViewGroup)?.setOnClickListener {
            startActivity(Intent(this, VeltraPayBillsActivity::class.java))
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

        // Secret Admin Entry
        binding.welcomeText.setOnLongClickListener {
            startActivity(Intent(this, VeltraAdminDashboardActivity::class.java))
            true
        }
        
        // Floating Center Pay Button
        binding.floatingPayBtn.setOnClickListener {
            startActivity(Intent(this, VeltraNFCPaymentActivity::class.java))
        }

        // Bottom Navigation Bar Items (Children of the first LinearLayout in bottomNav)
        val bottomNavContainer = (binding.bottomNav.getChildAt(0) as? ViewGroup)
        
        // Home (Index 0) - Already here
        
        // Wallet (Index 1)
        bottomNavContainer?.getChildAt(1)?.setOnClickListener {
            startActivity(Intent(this, VeltraWalletsActivity::class.java))
        }
        
        // Cards (Index 3 - Note: Index 2 is the space for the floating button)
        bottomNavContainer?.getChildAt(3)?.setOnClickListener {
            startActivity(Intent(this, VeltraCardsActivity::class.java))
        }
        
        // Profile (Index 4)
        bottomNavContainer?.getChildAt(4)?.setOnClickListener {
            startActivity(Intent(this, VeltraProfileActivity::class.java))
        }
    }
}
