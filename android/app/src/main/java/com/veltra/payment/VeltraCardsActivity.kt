package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraCardsBinding

class VeltraCardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Basic interactivity for the Cards screen
    }
}
