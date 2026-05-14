package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraTapAndPayBinding

class VeltraTapAndPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraTapAndPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraTapAndPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        
        // This screen would eventually trigger the NFC logic found in VeltraNFCPaymentActivity
    }
}
