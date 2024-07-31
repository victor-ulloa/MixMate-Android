package com.example.mixmate.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mixmate.data.Cocktail
import com.example.mixmate.databinding.FragmentRecipeCarouselItemBinding

import com.example.mixmate.databinding.FragmentRecipeListItemBinding
import com.example.mixmate.listeners.RecipeListOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeCarouselRecyclerViewAdapter(
    private val values: MutableList<Cocktail>,
    private val recipeListOnClickListener: RecipeListOnClickListener)
    : RecyclerView.Adapter<RecipeCarouselRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRecipeCarouselItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.recipeView.tag = item.id
        holder.recipeIdView.text = item.id.toString()
        holder.recipeNameView.text = item.name
        runBlocking {
            launch {
                Picasso.get()
                    .load(item.imageURL)
                    .resize(0,250)
                    .centerCrop()
                    .into(holder.recipeImageView)
            }
        }
        holder.recipeView.setOnClickListener{
            recipeListOnClickListener.onListItemClick(it, item)
        }
    }

    override fun getItemCount(): Int = values.size

    fun setData(data: List<Cocktail>){
        values.clear()
        values.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<Cocktail>){
        notifyItemRangeInserted(values.lastIndex, data.size)
        values.addAll(data)
    }

    fun removeData(position: Int){
        notifyItemRemoved(position)
        values.removeAt(position)
    }

    inner class ViewHolder(val binding: FragmentRecipeCarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recipeIdView : TextView = binding.id
        val recipeNameView : TextView = binding.name
        val recipeImageView : ImageView = binding.imageView

        val recipeView : View = binding.recipeView

        override fun toString(): String {
            return  recipeIdView.text.toString() + ". " + recipeNameView.text
        }
    }

}