package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraSendMoneyStep2Binding

class VeltraSendMoneyStep2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraSendMoneyStep2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraSendMoneyStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("recipient_name") ?: "John Doe"
        val phone = intent.getStringExtra("recipient_phone") ?: "+234 810 123 4567"

        // Update UI with recipient info (IDs based on step 2 layout)
        // Note: I need to ensure IDs exist or use findViewByID if binding fails for some views
        
        binding.backBtn.setOnClickListener { finish() }

        binding.continueBtn.setOnClickListener {
            val intent = Intent(this, VeltraPaymentSuccessActivity::class.java).apply {
                putExtra("amount", binding.amountInput.text.toString())
                putExtra("recipient_name", name)
                putExtra("recipient_phone", phone)
            }
            startActivity(intent)
        }
    }
}
