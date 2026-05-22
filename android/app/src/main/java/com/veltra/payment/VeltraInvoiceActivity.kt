package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraInvoiceBinding

class VeltraInvoiceActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraInvoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.createNewInvoiceBtn.setOnClickListener {
            Toast.makeText(this, "Invoice Creator Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    }
}
