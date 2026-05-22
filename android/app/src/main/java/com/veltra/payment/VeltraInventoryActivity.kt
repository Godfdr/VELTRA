package com.veltra.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veltra.payment.databinding.ActivityVeltraInventoryBinding
import com.veltra.payment.databinding.ItemInventoryBinding

data class InventoryItem(val name: String, val stock: Int, val price: Double)

class VeltraInventoryActivity : VeltraBaseActivity() {
    private lateinit var binding: ActivityVeltraInventoryBinding

    private val mockStock = listOf(
        InventoryItem("Wireless Earbuds", 15, 12500.0),
        InventoryItem("Power Bank 20k mAh", 8, 8500.0),
        InventoryItem("iPhone Case (Clear)", 42, 2500.0),
        InventoryItem("Fast Charger 20W", 20, 4000.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.addItemBtn.setOnClickListener {
            Toast.makeText(this, "Add Item Feature Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.inventoryRecyclerView.adapter = InventoryAdapter(mockStock)
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
            holder.binding.itemName.text = item.name
            holder.binding.itemStock.text = "In Stock: ${item.stock}"
            holder.binding.itemPrice.text = "₦ ${String.format("%,.0f", item.price)}"
        }

        override fun getItemCount() = items.size
    }
}
