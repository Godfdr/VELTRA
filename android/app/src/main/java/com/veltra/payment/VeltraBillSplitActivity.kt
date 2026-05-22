package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraBillSplitBinding

class VeltraBillSplitActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraBillSplitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraBillSplitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.recipientGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioMerchant) {
                Toast.makeText(this, "Funds will be sent directly to the Merchant upon completion", Toast.LENGTH_SHORT).show()
                binding.radioMerchant.setTextColor(resources.getColor(R.color.white, null))
                binding.radioPayer.setTextColor(resources.getColor(R.color.text_secondary, null))
            } else {
                Toast.makeText(this, "Funds will be sent to your wallet as reimbursement", Toast.LENGTH_SHORT).show()
                binding.radioPayer.setTextColor(resources.getColor(R.color.white, null))
                binding.radioMerchant.setTextColor(resources.getColor(R.color.text_secondary, null))
            }
        }

        binding.sendSplitBtn.setOnClickListener {
            val amount = binding.totalAmountInput.text.toString()
            if (amount.isNotEmpty()) {
                val recipientType = if (binding.radioPayer.isChecked) "Payer" else "Merchant"
                Toast.makeText(this, "Split Pings Sent! Total: ₦$amount\nRecipient: $recipientType", Toast.LENGTH_LONG).show()
                finish()
            } else {
                binding.totalAmountInput.error = "Enter total amount"
            }
        }
    }
}
