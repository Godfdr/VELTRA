package com.veltra.payment

import android.os.Bundle
import com.veltra.payment.databinding.ActivityVeltraCablePaymentBinding

class VeltraCablePaymentActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraCablePaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraCablePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
    }
}
