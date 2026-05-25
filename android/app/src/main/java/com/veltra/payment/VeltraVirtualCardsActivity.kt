package com.veltra.payment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraVirtualCardsBinding

class VeltraVirtualCardsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraVirtualCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraVirtualCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        
        binding.mintCardBtn.setOnClickListener {
            animateMinting()
        }

        binding.burnCardBtn.setOnClickListener {
            animateBurning()
        }
    }

    private fun animateMinting() {
        binding.mintCardBtn.isEnabled = false
        binding.mintCardBtn.text = "Minting..."
        
        binding.cardContainer.alpha = 0f
        binding.cardContainer.scaleX = 0.5f
        binding.cardContainer.scaleY = 0.5f
        binding.cardContainer.visibility = View.VISIBLE
        
        binding.cardContainer.animate()
            .alpha(1f)
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(1000)
            .withEndAction {
                binding.cardContainer.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(200)
                    .start()
                
                binding.mintCardBtn.visibility = View.GONE
                binding.burnCardBtn.visibility = View.VISIBLE
                Toast.makeText(this, "New Burner Minted! 💎", Toast.LENGTH_SHORT).show()
            }
            .start()
    }

    private fun animateBurning() {
        binding.burnCardBtn.isEnabled = false
        
        binding.cardContainer.animate()
            .alpha(0f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .rotation(5f)
            .setDuration(800)
            .withEndAction {
                binding.cardContainer.visibility = View.GONE
                binding.burnCardBtn.visibility = View.GONE
                binding.mintCardBtn.visibility = View.VISIBLE
                binding.mintCardBtn.isEnabled = true
                binding.mintCardBtn.text = "Mint New Burner"
                Toast.makeText(this, "Card Self-Destructed! 🔥", Toast.LENGTH_LONG).show()
            }
            .start()
    }
}
