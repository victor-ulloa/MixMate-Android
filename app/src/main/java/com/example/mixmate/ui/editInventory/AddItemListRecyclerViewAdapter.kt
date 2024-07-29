package com.example.mixmate.ui.editInventory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.databinding.FragmentToAddItemBinding
import com.example.mixmate.listeners.InventoryItemOnClickListener

class AddItemListRecyclerViewAdapter(
    private val values: List<InventoryItem>,
    val onClickListener: InventoryItemOnClickListener
) : RecyclerView.Adapter<AddItemListRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentToAddItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name

        holder.binding.root.setOnClickListener{
            onClickListener.onListItemClick(InventoryItem(item.id, item.name, item.type))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentToAddItemBinding): RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}