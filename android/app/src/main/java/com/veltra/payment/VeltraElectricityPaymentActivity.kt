package com.veltra.payment

import android.os.Bundle
import com.veltra.payment.databinding.ActivityVeltraElectricityPaymentBinding

class VeltraElectricityPaymentActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraElectricityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraElectricityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
    }
}
