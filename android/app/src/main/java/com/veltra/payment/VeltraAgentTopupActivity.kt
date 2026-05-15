package com.veltra.payment

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.veltra.payment.databinding.ActivityVeltraAgentTopupBinding
import com.veltra.payment.databinding.ItemAgentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

data class Agent(
    val id: String,
    val name: String,
    val status: String,
    val distance: Double,
    val isOpen: Boolean
)

class VeltraAgentTopupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraAgentTopupBinding
    private lateinit var agentAdapter: AgentAdapter
    private val client = OkHttpClient()
    
    private val currentUserId = "USER-7a2f9b1c3e4d"
    private var allAgents = mutableListOf<Agent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraAgentTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        generateAndShowQRCode(currentUserId)
        fetchAgentsFromServer()
        
        binding.backBtn.setOnClickListener { finish() }
        binding.simulateNfcBtn.setOnClickListener { showSuccessDialog() }
        
        startQrPulseAnimation()
    }

    private fun fetchAgentsFromServer() {
        binding.loadingProgress.visibility = View.VISIBLE
        binding.agentsRecyclerView.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000) // Fast simulation

            val mockData = listOf(
                Agent("1", "Veltra Hub - Lekki", "Open until 8:00 PM", 0.5, true),
                Agent("2", "Mama's Corner Store", "Open until 10:00 PM", 1.2, true),
                Agent("3", "Ikeja Express Point", "Closed - Opens 9 AM", 3.8, false),
                Agent("4", "Victoria Island Suite", "Open until 6:00 PM", 2.1, true),
                Agent("5", "Surulere Mini Mart", "Open 24 Hours", 4.5, true)
            )

            withContext(Dispatchers.Main) {
                allAgents.clear()
                allAgents.addAll(mockData)
                agentAdapter.updateData(allAgents)
                
                binding.loadingProgress.visibility = View.GONE
                binding.agentsRecyclerView.visibility = View.VISIBLE
                animateEntrance()
            }
        }
    }

    private fun generateAndShowQRCode(data: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            try {
                val size = 512
                val qrCodeWriter = QRCodeWriter()
                val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size)
                val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
                
                for (x in 0 until size) {
                    for (y in 0 until size) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                
                withContext(Dispatchers.Main) {
                    binding.qrCodeImage.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                // Fail silently or show placeholder
            }
        }
    }

    private fun showSuccessDialog() {
        Toast.makeText(this, "Top-Up Successful! ₦50.00 added.", Toast.LENGTH_LONG).show()
    }

    private fun startQrPulseAnimation() {
        val pulse = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        pulse.duration = 1000
        pulse.repeatMode = android.view.animation.Animation.REVERSE
        pulse.repeatCount = android.view.animation.Animation.INFINITE
        binding.qrCodeImage.startAnimation(pulse)
    }

    private fun setupRecyclerView() {
        agentAdapter = AgentAdapter(mutableListOf())
        binding.agentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@VeltraAgentTopupActivity)
            adapter = agentAdapter
        }
    }

    private fun setupSearch() {
        binding.locationInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterAgents(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterAgents(query: String) {
        val filtered = if (query.isEmpty()) {
            allAgents
        } else {
            allAgents.filter { it.name.contains(query, ignoreCase = true) }
        }
        agentAdapter.updateData(filtered)
    }

    private fun animateEntrance() {
        binding.qrSection.visibility = View.VISIBLE
        binding.qrSection.alpha = 0f
        binding.qrSection.translationY = 50f
        binding.qrSection.animate().alpha(1f).translationY(0f).setDuration(800).start()
    }

    class AgentAdapter(private var agents: List<Agent>) : RecyclerView.Adapter<AgentAdapter.ViewHolder>() {
        fun updateData(newAgents: List<Agent>) {
            agents = newAgents
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(agents[position])
        }

        override fun getItemCount() = agents.size

        class ViewHolder(private val binding: ItemAgentBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(agent: Agent) {
                binding.agentName.text = agent.name
                binding.agentStatus.text = agent.status
                binding.agentDistance.text = "${agent.distance} km"
                binding.statusIndicator.backgroundTintList = android.content.res.ColorStateList.valueOf(
                    if (agent.isOpen) 0xFF00D084.toInt() else 0xFFFF4757.toInt()
                )
            }
        }
    }
}
