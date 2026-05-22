package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraContactlessBinding

class VeltraContactlessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraContactlessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraContactlessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.phoneToPhoneBtn.setOnClickListener {
            // High-fidelity p2p NFC mode
            val intent = Intent(this, VeltraTapAndPayActivity::class.java).apply {
                putExtra("MODE", "P2P")
            }
            startActivity(intent)
        }

        binding.cardToPhoneBtn.setOnClickListener {
            // Read physical card mode
            Toast.makeText(this, "Scanning for Physical Card...", Toast.LENGTH_LONG).show()
            // In a real app, this would use NfcAdapter.enableReaderMode
        }
    }
}
