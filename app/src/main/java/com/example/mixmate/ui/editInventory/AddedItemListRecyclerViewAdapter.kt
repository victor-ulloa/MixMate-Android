package com.example.mixmate.ui.editInventory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.databinding.FragmentItemBinding

class AddedItemListRecyclerViewAdapter(
    private val values: MutableList<InventoryItem>
) : RecyclerView.Adapter<AddedItemListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.itemContent

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}