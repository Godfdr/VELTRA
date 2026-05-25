package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.veltra.payment.databinding.ActivityVeltraSavingsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VeltraSavingsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraSavingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraSavingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.generatePlanBtn.setOnClickListener {
            runAIArchitect()
        }
    }

    private fun runAIArchitect() {
        binding.generatePlanBtn.isEnabled = false
        binding.generatePlanBtn.text = "Architecting..."
        
        lifecycleScope.launch {
            delay(2000) // Simulate analysis
            binding.aiSuggestionText.text = "✨ Done! I've created the 'Friday Freedom' plan. Based on your income flow, you'll hit your goal 5 days early!"
            
            // Show Prediction
            binding.aiPredictionBadge.visibility = android.view.View.VISIBLE
            binding.aiPredictionBadge.alpha = 0f
            binding.aiPredictionBadge.animate().alpha(1f).setDuration(500).start()

            binding.generatePlanBtn.text = "Activate AI Plan 🚀"
            binding.generatePlanBtn.isEnabled = true
            
            binding.generatePlanBtn.setOnClickListener {
                Toast.makeText(this@VeltraSavingsActivity, "AI Savings Plan Active! 🛡️", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
