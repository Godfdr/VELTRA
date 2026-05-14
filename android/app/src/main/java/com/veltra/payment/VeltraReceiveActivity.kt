package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraReceiveBinding

class VeltraReceiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraReceiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraReceiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        
        binding.copyIdBtn.setOnClickListener {
            // Logic to copy ID to clipboard
        }
        
        binding.shareQrBtn.setOnClickListener {
            // Logic to share the QR code
        }
    }
}
