package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraSecuritySettingsBinding

class VeltraSecuritySettingsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraSecuritySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraSecuritySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.changePasswordBtn.setOnClickListener {
            startActivity(android.content.Intent(this, VeltraChangePasswordActivity::class.java))
        }

        binding.changePinBtn.setOnClickListener {
            startActivity(android.content.Intent(this, VeltraChangePinActivity::class.java))
        }
    }
}
