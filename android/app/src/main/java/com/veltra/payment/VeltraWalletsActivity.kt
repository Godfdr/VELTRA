package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.veltra.payment.databinding.ActivityVeltraWalletsBinding
import com.veltra.payment.databinding.ItemWalletBinding
import com.veltra.payment.network.ApiClient
import java.util.Locale

data class WalletPocket(
    val id: String,
    val name: String,
    val description: String,
    val balance: Double,
    val type: String = "SAVINGS",
    val target_amount: Long = 0
)

class VeltraWalletsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraWalletsBinding
    private lateinit var walletAdapter: WalletAdapter
    private var isBalanceVisible = true
    private var totalBalanceValue = 0.0

    private val pockets = mutableListOf<WalletPocket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityVeltraWalletsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        fetchPockets()
    }

    private fun fetchPockets() {
        ApiClient.get("/pockets") { success, data ->
            if (success && data != null) {
                val list = com.google.gson.Gson().fromJson(data, Array<WalletPocket>::class.java).toList()
                pockets.clear()
                pockets.addAll(list)
                walletAdapter.notifyDataSetChanged()
                updateTotalBalance()
            } else {
                Toast.makeText(this, "Failed to load pockets", Toast.LENGTH_SHORT).show()
                // Loading mock data if API fails for demo
                loadMockData()
            }
        }
    }

    private fun loadMockData() {
        pockets.clear()
        pockets.add(WalletPocket("1", "Main Wallet", "Primary spending account", 25600.50))
        pockets.add(WalletPocket("2", "Transport Wallet", "For commutes and rides", 8400.00))
        pockets.add(WalletPocket("3", "Savings Pocket", "Emergency and long-term funds", 120000.00))
        walletAdapter.notifyDataSetChanged()
        updateTotalBalance()
    }

    private fun updateTotalBalance() {
        totalBalanceValue = pockets.sumOf { it.balance }
        refreshBalanceDisplay()
    }

    private fun refreshBalanceDisplay() {
        if (isBalanceVisible) {
            binding.totalBalance.text = String.format(Locale.getDefault(), "₦ %,.2f", totalBalanceValue)
            binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_view)
        } else {
            binding.totalBalance.text = "₦ ••••••••"
            binding.balanceVisibilityBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        }
        walletAdapter.updateVisibility(isBalanceVisible)
    }

    private fun setupRecyclerView() {
        walletAdapter = WalletAdapter(pockets, isBalanceVisible) { pocket ->
            if (pocket.id == "5") {
                startActivity(Intent(this, VeltraOfflineWalletActivity::class.java))
            } else {
                Toast.makeText(this, "Opening ${pocket.name}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.walletsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@VeltraWalletsActivity)
            adapter = walletAdapter
        }
    }

    private fun setupListeners() {
        binding.header.findViewById<android.view.View>(R.id.backBtn).setOnClickListener { finish() }

        binding.balanceVisibilityBtn.setOnClickListener {
            isBalanceVisible = !isBalanceVisible
            refreshBalanceDisplay()
        }

        binding.createNewWalletBtn.setOnClickListener {
            showCreatePocketDialog()
        }

        binding.squadPocketCard.setOnClickListener {
            Toast.makeText(this, "Opening Summer Trip 2024 Squad Pocket... 🏖️", Toast.LENGTH_SHORT).show()
            // Logic to show group members and contributing history
        }
    }

    private fun showCreatePocketDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_pocket, null)
        dialog.setContentView(dialogView)
        
        val nameInput = dialogView.findViewById<EditText>(R.id.pocketNameInput)
        val amountInput = dialogView.findViewById<EditText>(R.id.pocketAmountInput)
        val createBtn = dialogView.findViewById<Button>(R.id.createPocketBtn)
        
        createBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val balanceStr = amountInput.text.toString()
            
            if (name.isNotEmpty() && balanceStr.isNotEmpty()) {
                val newPocket = WalletPocket(
                    "",
                    name,
                    "Custom pocket",
                    balanceStr.toDouble()
                )
                
                ApiClient.post("/pockets", newPocket) { success, _ ->
                    if (success) {
                        fetchPockets()
                        dialog.dismiss()
                        Toast.makeText(this, "$name pocket created! ✅", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Failed to create pocket", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        
        dialog.show()
    }

    class WalletAdapter(
        private val items: List<WalletPocket>,
        private var isVisible: Boolean,
        private val onItemClick: (WalletPocket) -> Unit
    ) : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

        fun updateVisibility(visible: Boolean) {
            isVisible = visible
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item, isVisible)
            holder.itemView.setOnClickListener { onItemClick(item) }
        }

        override fun getItemCount() = items.size

        class ViewHolder(private val binding: ItemWalletBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: WalletPocket, isVisible: Boolean) {
                binding.walletName.text = item.name
                binding.walletDescription.text = item.description
                if (isVisible) {
                    binding.walletBalance.text = String.format(Locale.getDefault(), "₦ %,.2f", item.balance)
                } else {
                    binding.walletBalance.text = "₦ ••••"
                }
                
                val color = when(item.id) {
                    "1" -> 0xFF00D4FF.toInt()
                    "2" -> 0xFF00D084.toInt()
                    "3" -> 0xFF7000FF.toInt()
                    "4" -> 0xFFFFA502.toInt()
                    else -> 0xFFFF4757.toInt()
                }
                binding.walletIconBackground.backgroundTintList = android.content.res.ColorStateList.valueOf(color)
            }
        }
    }
}
