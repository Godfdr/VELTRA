package com.veltra.payment

import android.os.Bundle
import com.veltra.payment.databinding.ActivityVeltraItemizedReceiptBinding

class VeltraItemizedReceiptActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraItemizedReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraItemizedReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.doneBtn.setOnClickListener { finish() }
    }
}
