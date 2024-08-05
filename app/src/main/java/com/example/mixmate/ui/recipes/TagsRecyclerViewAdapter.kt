package com.example.mixmate.ui.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.TagRvListItemBinding
import com.example.mixmate.listeners.TagOnClickListener

class TagsRecyclerViewAdapter(
    private val tagOnClickListener: TagOnClickListener
)
    : RecyclerView.Adapter<TagsRecyclerViewAdapter.ViewHolder>() {

        private val values: Array<Constants.Tags>
        = enumValues<Constants.Tags>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TagRvListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tagView.text = item.toString()
        holder.tagView.textOn = item.toString()
        holder.tagView.textOff = item.toString()

        holder.tagView.setOnClickListener{
            tagOnClickListener.onTagClick(it, item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: TagRvListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val tagView: ToggleButton = binding.toggleButton

        override fun toString(): String {
            return tagView.text.toString()
        }
            }
}