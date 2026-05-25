package com.veltra.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraRemittanceBinding
import java.util.Locale

class VeltraRemittanceActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraRemittanceBinding
    private val exchangeRate = 1500.0 // 1 USD = 1500 NGN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraRemittanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupConverter()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
        
        binding.countrySelector.setOnClickListener {
            Toast.makeText(this, "Country selection coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.continueBtn.setOnClickListener {
            Toast.makeText(this, "Connecting to Global Rail...", Toast.LENGTH_LONG).show()
            // In real app, navigate to recipient detail entry
        }
    }

    private fun setupConverter() {
        binding.amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateConverted(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun calculateConverted(input: String) {
        if (input.isEmpty()) {
            binding.receiveAmount.text = "0.00"
            return
        }
        
        try {
            val amountNgn = input.replace(",", "").toDouble()
            val amountUsd = amountNgn / exchangeRate
            binding.receiveAmount.text = String.format(Locale.getDefault(), "%,.2f", amountUsd)
        } catch (e: Exception) {
            binding.receiveAmount.text = "0.00"
        }
    }
}
