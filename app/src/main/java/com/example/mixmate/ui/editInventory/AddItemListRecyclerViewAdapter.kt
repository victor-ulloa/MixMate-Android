package com.example.mixmate.ui.editInventory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.databinding.FragmentItem2Binding
import com.example.mixmate.listeners.InventoryItemOnClickListener

class AddItemListRecyclerViewAdapter(
    private val values: List<InventoryItem>,
    val onClickListener: InventoryItemOnClickListener
) : RecyclerView.Adapter<AddItemListRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item.id.toString()
        holder.contentView.text = item.name

        holder.itemView.setOnClickListener{
            onClickListener.onListItemClick(InventoryItem(item.id, item.name, item.type))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItem2Binding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}