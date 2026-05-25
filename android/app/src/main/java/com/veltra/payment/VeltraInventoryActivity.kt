package com.veltra.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veltra.payment.databinding.ActivityVeltraInventoryBinding
import com.veltra.payment.databinding.ItemInventoryBinding
import com.veltra.payment.network.ApiClient
import java.util.Locale

data class InventoryItem(
    val id: String = "",
    val name: String, 
    val stock_count: Int, 
    val selling_price: Double,
    val cost_price: Double
)

class VeltraInventoryActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraInventoryBinding
    private val items = mutableListOf<InventoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.addItemBtn.setOnClickListener {
            Toast.makeText(this, "Add Item Feature Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        fetchInventory()
    }

    private fun fetchInventory() {
        ApiClient.get("/merchant/inventory") { success, data ->
            if (success && data != null) {
                val list = com.google.gson.Gson().fromJson(data, Array<InventoryItem>::class.java).toList()
                items.clear()
                items.addAll(list)
                binding.inventoryRecyclerView.adapter?.notifyDataSetChanged()
            } else {
                // Fallback to mock
                loadMockInventory()
            }
        }
    }

    private fun loadMockInventory() {
        items.clear()
        items.add(InventoryItem(name = "Wireless Earbuds", stock_count = 15, selling_price = 12500.0, cost_price = 8000.0))
        items.add(InventoryItem(name = "Power Bank 20k", stock_count = 8, selling_price = 8500.0, cost_price = 6200.0))
        binding.inventoryRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = InventoryAdapter(items)
    }

    class InventoryAdapter(private val items: List<InventoryItem>) :
        RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

        class ViewHolder(val binding: ItemInventoryBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            val profit = item.selling_price - item.cost_price
            
            holder.binding.itemName.text = item.name
            holder.binding.itemStock.text = "In Stock: ${item.stock_count}"
            holder.binding.itemPrice.text = String.format(Locale.getDefault(), "₦ %,.0f", item.selling_price)
            holder.binding.itemProfit.text = String.format(Locale.getDefault(), "Profit/unit: ₦ %,.0f", profit)
        }

        override fun getItemCount() = items.size
    }
}
