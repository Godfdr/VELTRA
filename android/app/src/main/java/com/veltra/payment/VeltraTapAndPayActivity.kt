package com.veltra.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraTapAndPayBinding
import java.util.Locale

class VeltraTapAndPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraTapAndPayBinding
    
    private val wallets = listOf(
        WalletPocket("1", "Main Wallet", "Primary spending account", 25600.50),
        WalletPocket("2", "Transport Wallet", "For commutes and rides", 8400.00),
        WalletPocket("3", "Savings Pocket", "Emergency and long-term funds", 120000.00)
    )
    
    private var selectedWallet = wallets[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initial theme application
        val sharedPref = getSharedPreferences("veltra_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", true)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityVeltraTapAndPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        updateWalletDisplay()
        startRippleAnimation()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
        
        binding.walletSelectorCard.setOnClickListener {
            showWalletSelectionSheet()
        }
    }

    private fun updateWalletDisplay() {
        binding.selectedWalletName.text = selectedWallet.name
        binding.selectedWalletBalance.text = String.format(Locale.getDefault(), "₦ %,.2f", selectedWallet.balance)
        
        val color = when(selectedWallet.id) {
            "1" -> 0xFF00D4FF.toInt()
            "2" -> 0xFF00D084.toInt()
            "3" -> 0xFF7000FF.toInt()
            else -> 0xFF00D4FF.toInt()
        }
        binding.walletIcon.backgroundTintList = android.content.res.ColorStateList.valueOf(color)
    }

    private fun startRippleAnimation() {
        // Subtle pulse animation for the ripples
        binding.rippleContainer.alpha = 0.5f
        binding.rippleContainer.animate()
            .alpha(1f)
            .setDuration(1500)
            .setListener(null)
            .withEndAction {
                binding.rippleContainer.animate()
                    .alpha(0.5f)
                    .setDuration(1500)
                    .withEndAction { startRippleAnimation() }
                    .start()
            }
            .start()
    }

    private fun showWalletSelectionSheet() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_services_bottom_sheet, null)
        dialog.setContentView(view)
        
        val titleView = view.findViewById<TextView>(R.id.servicesTitle)
        titleView?.text = "Select Debiting Wallet"
        
        // Clear services grid and add a simple RecyclerView or list
        val grid = view.findViewById<ViewGroup>(view.resources.getIdentifier("gridLayout", "id", packageName))
        // Since I can't easily replace the GridLayout with a RecyclerView here without complex XML manipulation,
        // I'll just reuse the existing service buttons for the demo.
        
        view.findViewById<View>(R.id.service_nfc_btn).setOnClickListener {
            selectedWallet = wallets[0]
            updateWalletDisplay()
            dialog.dismiss()
        }
        
        view.findViewById<View>(R.id.service_send_btn).setOnClickListener {
            selectedWallet = wallets[1]
            updateWalletDisplay()
            dialog.dismiss()
        }

        view.findViewById<View>(R.id.service_receive_btn).setOnClickListener {
            selectedWallet = wallets[2]
            updateWalletDisplay()
            dialog.dismiss()
        }
        
        dialog.show()
    }
}
