package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraTopUpMethodBinding

class VeltraTopUpMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraTopUpMethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraTopUpMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.agentMethodBtn.setOnClickListener {
            startActivity(Intent(this, VeltraAgentTopupActivity::class.java))
        }

        binding.continueBtn.setOnClickListener {
            // For other methods, just simulate success or next step
            finish()
        }
    }
}
