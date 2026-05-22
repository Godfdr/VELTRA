package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraMerchantDashboardBinding

/**
 * VELTRA BUSINESS - MERCHANT DASHBOARD
 * Features:
 * 1. Phone as POS (NFC Receive)
 * 2. Sales Analytics
 * 3. Customer management
 */
class VeltraMerchantDashboardActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraMerchantDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraMerchantDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        animateEntrance()
    }

    private fun setupListeners() {
        binding.switchBackBtn.setOnClickListener {
            // Switch back to Personal Mode
            val intent = Intent(this, VeltraMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.phonePosBtn.setOnClickListener {
            // Activate the "Phone as POS" (NFC Receive mode)
            // Reusing the Tap & Pay screen but in RECEIVE mode
            val intent = Intent(this, VeltraTapAndPayActivity::class.java).apply {
                putExtra("MODE", "MERCHANT_RECEIVE")
            }
            startActivity(intent)
        }

        // Mock clicks for business hub
        binding.root.findViewById<android.view.View>(R.id.totalSalesValue).setOnClickListener {
            Toast.makeText(this, "Detailed Sales Analytics Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun animateEntrance() {
        binding.phonePosBtn.alpha = 0f
        binding.phonePosBtn.translationY = 50f
        binding.phonePosBtn.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .setStartDelay(200)
            .start()
    }
}
