package com.veltra.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.veltra.payment.databinding.ActivityVeltraRemittanceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Locale

data class ExchangeResponse(val rates: Map<String, Double>)

class VeltraRemittanceActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraRemittanceBinding
    private var liveExchangeRate = 1500.0 // Default fallback
    private val serviceChargePercent = 0.015 // 1.5%
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraRemittanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupConverter()
        fetchLiveRate()
    }

    private fun fetchLiveRate() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Free API for live rates
                val request = Request.Builder()
                    .url("https://api.exchangerate-api.com/v4/latest/USD")
                    .build()
                
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val data = Gson().fromJson(body, ExchangeResponse::class.java)
                    val ngnRate = data.rates["NGN"] ?: 1500.0
                    
                    withContext(Dispatchers.Main) {
                        liveExchangeRate = ngnRate
                        binding.rateText.text = String.format(Locale.getDefault(), "1 USD = ₦ %,.2f", liveExchangeRate)
                        calculateConverted(binding.amountInput.text.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("Remittance", "Failed to fetch rate", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@VeltraRemittanceActivity, "Using offline rate", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
        
        binding.countrySelector.setOnClickListener {
            Toast.makeText(this, "Country selection coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.continueBtn.setOnClickListener {
            Toast.makeText(this, "Processing Global Transfer...", Toast.LENGTH_LONG).show()
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
            binding.feeText.text = "Fee (1.5%): ₦ 0.00"
            return
        }
        
        try {
            val amountNgn = input.replace(",", "").toDouble()
            
            // Calculate 1.5% fee in NGN
            val feeNgn = amountNgn * serviceChargePercent
            val netAmountNgn = amountNgn - feeNgn
            
            // Convert to USD
            val amountUsd = netAmountNgn / liveExchangeRate
            
            binding.receiveAmount.text = String.format(Locale.getDefault(), "%,.2f", amountUsd)
            binding.feeText.text = String.format(Locale.getDefault(), "Fee (1.5%): ₦ %,.2f", feeNgn)
            
        } catch (e: Exception) {
            binding.receiveAmount.text = "0.00"
            binding.feeText.text = "Fee (1.5%): ₦ 0.00"
        }
    }
}
