package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraPayBillsBinding

class VeltraPayBillsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraPayBillsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraPayBillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.dataBtn.setOnClickListener {
            startActivity(android.content.Intent(this, VeltraDataPaymentActivity::class.java))
        }

        binding.tvBtn.setOnClickListener {
            startActivity(android.content.Intent(this, VeltraCablePaymentActivity::class.java))
        }

        binding.electricityBtn.setOnClickListener {
            startActivity(android.content.Intent(this, VeltraElectricityPaymentActivity::class.java))
        }
    }
}
