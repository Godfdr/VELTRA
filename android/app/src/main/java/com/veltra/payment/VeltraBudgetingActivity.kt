package com.veltra.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraBudgetingBinding

class VeltraBudgetingActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraBudgetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraBudgetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        
        binding.setPaydayRuleBtn.setOnClickListener {
            showPaydayRuleDialog()
        }
    }

    private fun showPaydayRuleDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payday_rule, null)
        dialog.setContentView(dialogView)

        val saveBtn = dialogView.findViewById<Button>(R.id.saveRuleBtn)
        val destinationInput = dialogView.findViewById<EditText>(R.id.destinationInput)
        val radio10 = dialogView.findViewById<RadioButton>(R.id.radio10)
        val radio20 = dialogView.findViewById<RadioButton>(R.id.radio20)
        val radio50 = dialogView.findViewById<RadioButton>(R.id.radio50)

        saveBtn.setOnClickListener {
            val destination = destinationInput.text.toString()
            val percentage = when {
                radio10.isChecked -> "10%"
                radio20.isChecked -> "20%"
                radio50.isChecked -> "50%"
                else -> "20%"
            }

            if (destination.isNotEmpty()) {
                // Persistent storage
                val sharedPref = getSharedPreferences("veltra_prefs", MODE_PRIVATE)
                sharedPref.edit().putString("payday_destination", destination).apply()
                sharedPref.edit().putString("payday_percentage", percentage).apply()

                Toast.makeText(this, "Payday Rule: $percentage salary to $destination! 💰", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            } else {
                destinationInput.error = "Enter destination pocket"
            }
        }

        dialog.show()
    }
}
