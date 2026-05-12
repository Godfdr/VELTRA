package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraMainBinding

class VeltraMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVeltraMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Use ViewBinding for easier UI manipulation
        binding = ActivityVeltraMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup UI interactions based on layout
        setupListeners()
    }

    private fun setupListeners() {
        // Example: Handle floating pay button click
        // binding.bottomNav...
    }
}
