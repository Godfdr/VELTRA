package com.veltra.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.veltra.payment.databinding.ActivityVeltraProfileBinding

class VeltraProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupThemeSwitch()
        setupListeners()
    }

    private fun setupThemeSwitch() {
        val sharedPref = getSharedPreferences("veltra_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", true)
        
        binding.themeSwitch.isChecked = isDarkMode

        binding.themeBtn.setOnClickListener {
            val newState = !binding.themeSwitch.isChecked
            binding.themeSwitch.isChecked = newState
            sharedPref.edit {
                putBoolean("dark_mode", newState)
            }
            
            if (newState) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.settingsBtn.setOnClickListener {
            Toast.makeText(this, "Settings coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.personalInfoBtn.setOnClickListener {
            Toast.makeText(this, "Opening Personal Info", Toast.LENGTH_SHORT).show()
        }

        binding.kycBtn.setOnClickListener {
            Toast.makeText(this, "Verification Status: Level 3", Toast.LENGTH_SHORT).show()
        }

        binding.securityBtn.setOnClickListener {
            Toast.makeText(this, "Opening Security Settings", Toast.LENGTH_SHORT).show()
        }

        binding.logoutBtn.setOnClickListener {
            startActivity(Intent(this, VeltraLoginActivity::class.java))
            finishAffinity()
        }
    }
}
