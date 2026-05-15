package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraCardsBinding

class VeltraCardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraCardsBinding
    private var isCardLocked = false
    private var isNumberVisible = false
    private val actualNumber = "4023   8891   0012   1234"
    private val hiddenNumber = "••••   ••••   ••••   1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        updateNumberDisplay()
    }

    private fun updateNumberDisplay() {
        if (isNumberVisible) {
            binding.cardNumber.text = actualNumber
            binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_view)
        } else {
            binding.cardNumber.text = hiddenNumber
            binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        }
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.balanceVisibilityBtn.setOnClickListener {
            isNumberVisible = !isNumberVisible
            updateNumberDisplay()
        }

        binding.lockCardBtn.setOnClickListener {
            isCardLocked = !isCardLocked
            if (isCardLocked) {
                binding.cardContainer.alpha = 0.5f
                Toast.makeText(this, "Card Locked! 🔒", Toast.LENGTH_SHORT).show()
                // Update text logic would go here if I wanted to change "Lock Card" to "Unlock"
            } else {
                binding.cardContainer.alpha = 1.0f
                Toast.makeText(this, "Card Unlocked! ✅", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardSettingsBtn.setOnClickListener {
            Toast.makeText(this, "Opening Card Settings...", Toast.LENGTH_SHORT).show()
        }

        binding.limitsBtn.setOnClickListener {
            Toast.makeText(this, "Opening Transaction Limits...", Toast.LENGTH_SHORT).show()
        }
    }
}

