package com.veltra.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraTapAndPayBinding
import java.util.Locale

class VeltraTapAndPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraTapAndPayBinding
    private var selectedWalletName = "Main Wallet"
    private var selectedWalletBalance = 25600.50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraTapAndPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        updateWalletDisplay()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
        
        binding.walletSelectorCard.setOnClickListener {
            showWalletSelectionSheet()
        }
    }

    private fun updateWalletDisplay() {
        binding.selectedWalletName.text = selectedWalletName
        binding.selectedWalletBalance.text = String.format(Locale.getDefault(), "₦ %,.2f", selectedWalletBalance)
    }

    private fun showWalletSelectionSheet() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_services_bottom_sheet, null)
        
        val titleView = view.findViewById<TextView>(R.id.servicesTitle)
        titleView?.text = "Select Debiting Wallet"
        
        dialog.setContentView(view)
        dialog.show()
        
        view.findViewById<View>(R.id.service_nfc_btn).setOnClickListener {
            selectedWalletName = "Main Wallet"
            selectedWalletBalance = 25600.50
            updateWalletDisplay()
            dialog.dismiss()
        }
        
        view.findViewById<View>(R.id.service_send_btn).setOnClickListener {
            selectedWalletName = "Transport Wallet"
            selectedWalletBalance = 8400.00
            updateWalletDisplay()
            dialog.dismiss()
        }

        view.findViewById<View>(R.id.service_receive_btn).setOnClickListener {
            selectedWalletName = "Savings Pocket"
            selectedWalletBalance = 120000.00
            updateWalletDisplay()
            dialog.dismiss()
        }
    }
}
