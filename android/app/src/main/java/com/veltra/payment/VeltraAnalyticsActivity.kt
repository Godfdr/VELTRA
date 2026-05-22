package com.veltra.payment

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.veltra.payment.databinding.ActivityVeltraAnalyticsBinding

class VeltraAnalyticsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraAnalyticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityVeltraAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        animateEntrance()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun animateEntrance() {
        binding.monthlyTotal.alpha = 0f
        binding.monthlyTotal.translationY = 50f
        binding.monthlyTotal.animate().alpha(1f).translationY(0f).setDuration(800).start()
    }
}
