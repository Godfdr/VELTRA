package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraPaymentSuccessBinding

class VeltraPaymentSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraPaymentSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount = intent.getStringExtra("amount") ?: "5,000.00"
        val name = intent.getStringExtra("recipient_name") ?: "John Doe"
        val phone = intent.getStringExtra("recipient_phone") ?: "+234 810 123 4567"

        binding.successAmount.text = "₦ $amount"
        binding.successSub.text = "Sent to $name\n$phone"

        binding.doneBtn.setOnClickListener {
            val intent = Intent(this, VeltraMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.backBtn.setOnClickListener { finish() }
    }
}
