package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraOfflineWalletBinding

class VeltraOfflineWalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraOfflineWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraOfflineWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.backBtn.setOnClickListener { finish() }
    }
}
