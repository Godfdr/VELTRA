package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraChangePasswordBinding

class VeltraChangePasswordActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.saveBtn.setOnClickListener {
            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
