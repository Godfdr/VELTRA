package com.veltra.payment

import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.veltra.payment.databinding.ActivityVeltraCardsBinding

class VeltraCardsActivity : VeltraBaseActivity() {
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
        startLogoPulse()
        loadSavedSkin()
    }

    private fun startLogoPulse() {
        val pulse = ScaleAnimation(
            1.0f, 1.1f, 1.0f, 1.1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        pulse.duration = 1000
        pulse.repeatMode = Animation.REVERSE
        pulse.repeatCount = Animation.INFINITE
        binding.cardBgLogo.startAnimation(pulse)
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

        binding.skinDefault.setOnClickListener { updateSkin("#1A1D2D", "default") }
        binding.skinGold.setOnClickListener { updateSkin("#D4AF37", "gold") }
        binding.skinNeon.setOnClickListener { updateSkin("#001F1F", "neon") }
        binding.skinEco.setOnClickListener { updateSkin("#004D40", "eco") }

        binding.lockCardBtn.setOnClickListener {
            isCardLocked = !isCardLocked
            binding.cardContainer.alpha = if (isCardLocked) 0.5f else 1.0f
            Toast.makeText(this, if (isCardLocked) "Card Locked! 🔒" else "Card Unlocked! ✅", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSkin(colorCode: String, skinName: String) {
        binding.cardInnerLayout.setBackgroundColor(Color.parseColor(colorCode))
        val sharedPref = getSharedPreferences("veltra_prefs", MODE_PRIVATE)
        sharedPref.edit {
            putString("card_skin", colorCode)
        }
        Toast.makeText(this, "Skin Applied: $skinName", Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedSkin() {
        val sharedPref = getSharedPreferences("veltra_prefs", MODE_PRIVATE)
        val savedColor = sharedPref.getString("card_skin", "#1A1D2D")
        binding.cardInnerLayout.setBackgroundColor(Color.parseColor(savedColor))
    }
}
