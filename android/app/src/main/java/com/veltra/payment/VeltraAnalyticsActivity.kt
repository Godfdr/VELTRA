package com.veltra.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veltra.payment.databinding.ActivityVeltraAnalyticsBinding
import com.veltra.payment.databinding.ItemInventoryBinding // Reusing this for high-fidelity list
import java.util.Locale

data class PurchaseRecord(
    val merchant: String,
    val item: String,
    val amount: Double,
    val date: String
)

class VeltraAnalyticsActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraAnalyticsBinding

    private val purchaseHistory = listOf(
        PurchaseRecord("Veltra Tech Store", "Wireless Earbuds", 12500.0, "May 25, 9:41 AM"),
        PurchaseRecord("Market Square", "Weekly Groceries", 1550.0, "May 24, 2:30 PM"),
        PurchaseRecord("BRT Ride", "Lekki to Ikoyi", 200.0, "May 24, 8:15 AM"),
        PurchaseRecord("Mama Corner", "Lunch Combo", 1200.0, "May 23, 1:05 PM")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityVeltraAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupHistoryList()
        animateEntrance()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun setupHistoryList() {
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = PurchaseHistoryAdapter(purchaseHistory)
    }

    private fun animateEntrance() {
        binding.monthlyTotal.alpha = 0f
        binding.monthlyTotal.translationY = 50f
        binding.monthlyTotal.animate().alpha(1f).translationY(0f).setDuration(800).start()
    }

    class PurchaseHistoryAdapter(private val items: List<PurchaseRecord>) :
        RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder>() {

        class ViewHolder(val binding: ItemInventoryBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.binding.itemName.text = item.merchant
            holder.binding.itemStock.text = item.item
            holder.binding.itemProfit.text = item.date
            holder.binding.itemPrice.text = String.format(Locale.getDefault(), "₦ %,.0f", item.amount)
            
            // Customize style for history
            holder.binding.itemProfit.setTextColor(0xFF9CA3AF.toInt()) // Gray
        }

        override fun getItemCount() = items.size
    }
}
