package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraSendMoneyStep1Binding

class VeltraSendMoneyStep1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraSendMoneyStep1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraSendMoneyStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        // Simplified contact click to move to step 2
        binding.c1Name.setOnClickListener { navigateToStep2("Aisha Lawal", "+234 810 123 4567") }
        binding.c2Name.setOnClickListener { navigateToStep2("Bala Ahmed", "+234 905 765 4321") }
    }

    private fun navigateToStep2(name: String, phone: String) {
        val intent = Intent(this, VeltraSendMoneyStep2Activity::class.java).apply {
            putExtra("recipient_name", name)
            putExtra("recipient_phone", phone)
        }
        startActivity(intent)
    }
}
