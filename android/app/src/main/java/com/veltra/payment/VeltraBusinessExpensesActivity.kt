package com.veltra.payment

import android.os.Bundle
import android.widget.Toast
import com.veltra.payment.databinding.ActivityVeltraBusinessExpensesBinding

class VeltraBusinessExpensesActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraBusinessExpensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraBusinessExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.addExpenseBtn.setOnClickListener {
            Toast.makeText(this, "Expense Logger Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    }
}
