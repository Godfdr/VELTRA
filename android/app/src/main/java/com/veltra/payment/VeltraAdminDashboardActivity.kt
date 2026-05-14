package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraAdminDashboardBinding

class VeltraAdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
