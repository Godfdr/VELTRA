package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraReceiveBinding

class VeltraReceiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraReceiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraReceiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        animateEntrance()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }

        binding.pingOption.setOnClickListener {
            showPingDialog()
        }

        binding.nfcOption.setOnClickListener {
            Toast.makeText(this, "NFC Receiving Active. Hold phone near friend.", Toast.LENGTH_LONG).show()
            // In a real app, this would start the NFC NDEF push or Host Card Emulation
        }

        binding.bankOption.setOnClickListener {
            // Toggle visibility or copy account number
            val accountNumber = binding.accountNumber.text.toString()
            Toast.makeText(this, "Account Number $accountNumber copied!", Toast.LENGTH_SHORT).show()
        }

        binding.agentOption.setOnClickListener {
            val intent = Intent(this, VeltraAgentTopupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showPingDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_pocket, null)
        dialog.setContentView(dialogView)

        val title = dialogView.findViewById<android.widget.TextView>(R.id.dialogTitle)
        title.text = "Spot a Friend"
        
        val desc = dialogView.findViewById<android.widget.TextView>(R.id.dialogTitle).apply { 
            // In a real app we'd have a separate desc ID, but I'll just change the text or hide it
        }

        val nameInput = dialogView.findViewById<android.widget.EditText>(R.id.pocketNameInput)
        nameInput.hint = "Friend's @Username"
        
        val amountInput = dialogView.findViewById<android.widget.EditText>(R.id.pocketAmountInput)
        val requestBtn = dialogView.findViewById<android.widget.Button>(R.id.createPocketBtn)
        requestBtn.text = "Ping Friend"

        requestBtn.setOnClickListener {
            val amount = amountInput.text.toString()
            if (amount.isNotEmpty()) {
                Toast.makeText(this, "Ping sent to friend for ₦$amount!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            } else {
                amountInput.error = "Enter amount"
            }
        }

        dialog.show()
    }

    private fun animateEntrance() {
        binding.titleText.alpha = 0f
        binding.titleText.translationY = -20f
        binding.titleText.animate().alpha(1f).translationY(0f).setDuration(500).start()
        
        binding.logoImage.alpha = 0f
        binding.logoImage.animate().alpha(1f).setDuration(800).start()
    }
}

