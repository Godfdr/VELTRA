package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraBudgetingBinding

class VeltraBudgetingActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraBudgetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraBudgetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        
        binding.setPaydayRuleBtn.setOnClickListener {
            Toast.makeText(this, "Auto-Payday Rule Set: 20% to Savings! 💰", Toast.LENGTH_LONG).show()
        }
    }
}
