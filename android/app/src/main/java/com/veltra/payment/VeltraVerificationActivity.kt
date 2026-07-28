package com.veltra.payment

import android.os.Bundle
import com.veltra.payment.databinding.ActivityVeltraVerificationBinding

class VeltraVerificationActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
    }
}
