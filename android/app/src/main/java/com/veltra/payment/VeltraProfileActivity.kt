package com.veltra.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.veltra.payment.databinding.ActivityVeltraProfileBinding

class VeltraProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
