package com.veltra.payment

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class VeltraBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPref = getSharedPreferences("veltra_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", true)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.pointerCount == 3) {
            // Premium Ghost Mode: 3-finger long press (simulated by count check)
            Toast.makeText(this, "Ghost Mode Triggered 👻", Toast.LENGTH_SHORT).show()
        }
        return super.dispatchTouchEvent(ev)
    }

    fun triggerOfflineHaptic() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as android.os.Vibrator
        val pattern = longArrayOf(0, 50, 100, 50) // Double Pulse
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(android.os.VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }
}
