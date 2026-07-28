package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraChangePinBinding

class VeltraChangePinActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraChangePinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraChangePinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.saveBtn.setOnClickListener {
            Toast.makeText(this, "PIN updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
