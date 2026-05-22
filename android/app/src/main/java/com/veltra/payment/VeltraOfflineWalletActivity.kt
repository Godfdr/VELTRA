package com.veltra.payment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.veltra.payment.databinding.ActivityVeltraOfflineWalletBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * VELTRA PREMIUM OFFLINE WALLET
 * Mechanism: 
 * 1. Reserve: User "locks" funds from Main Wallet into the phone's Secure Enclave while online.
 * 2. Transact: NFC/QR generates a signed offline token verified by the merchant's Veltra App.
 * 3. Settlement: Transaction syncs automatically once either device gets internet.
 */
class VeltraOfflineWalletActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraOfflineWalletBinding
    private var offlineBalance = 12000.00
    private var isSyncing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraOfflineWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        binding.offlineBalanceValue.text = String.format(Locale.getDefault(), "₦ %,.2f", offlineBalance)
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.reserveFundsBtn.setOnClickListener {
            showReserveDialog()
        }

        binding.syncNowBtn.setOnClickListener {
            simulateSync()
        }

        binding.activateOfflinePayBtn.setOnClickListener {
            Toast.makeText(this, "NFC Offline Mode Active 📡", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showReserveDialog() {
        // High-end feel: Tapping into the Main Wallet to "Pull" funds
        Toast.makeText(this, "Reserving ₦5,000 from Main Wallet...", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            delay(1500)
            offlineBalance += 5000.00
            setupUI()
            Toast.makeText(this@VeltraOfflineWalletActivity, "Funds Secured for Offline Use ✅", Toast.LENGTH_LONG).show()
        }
    }

    private fun simulateSync() {
        if (isSyncing) return
        
        isSyncing = true
        binding.syncProgress.visibility = View.VISIBLE
        binding.syncStatusText.text = "Syncing Transactions..."
        
        lifecycleScope.launch {
            delay(3000) // Simulate network upload
            isSyncing = false
            binding.syncProgress.visibility = View.GONE
            binding.syncStatusText.text = "All transactions synced with Veltra Ledger"
            Toast.makeText(this@VeltraOfflineWalletActivity, "Cloud Sync Complete ☁️", Toast.LENGTH_SHORT).show()
        }
    }
}
