package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraWalletsBinding

class VeltraWalletsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraWalletsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraWalletsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.header.findViewById<android.view.View>(android.R.id.content)?.setOnClickListener { finish() } // Simulating back for now or add a back button

        binding.offlineWalletPocket.setOnClickListener {
            startActivity(Intent(this, VeltraOfflineWalletActivity::class.java))
        }

        binding.createNewWalletBtn.setOnClickListener {
            // Logic to create a new pocket
        }
    }
}
