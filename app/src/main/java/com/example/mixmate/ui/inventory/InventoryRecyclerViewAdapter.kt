package com.example.mixmate.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.FragmentInventoryItemBinding
import com.example.mixmate.listeners.InventoryOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InventoryRecyclerViewAdapter (
    private val values: Array<Constants.InventoryItemType>,
    private val onClickListener: InventoryOnClickListener
): RecyclerView.Adapter<InventoryRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FragmentInventoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.categoryImageView
        val textView = binding.categoryTextView
        val frameLayout = binding.frameLayout

        override fun toString(): String {
            return  textView.text.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentInventoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.frameLayout.tag = item.name
        holder.textView.text = item.name
        runBlocking {
            launch {
                Picasso.get()
                    .load(item.getImageURL())
                    .resize(300,200)
                    .centerCrop()
                    .into(holder.imageView)
            }
        }
        holder.frameLayout.setOnClickListener {
            onClickListener.onClick(it)
        }
    }
}