package com.veltra.payment

import android.os.Bundle
import com.veltra.payment.databinding.ActivityVeltraDataPaymentBinding

class VeltraDataPaymentActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraDataPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraDataPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
    }
}
