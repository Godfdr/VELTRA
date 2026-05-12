package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class VeltraSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veltra_splash)

        // Simulate splash delay and transition to Main Dashboard
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, VeltraMainActivity::class.java))
            finish()
        }, 2000)
    }
}
