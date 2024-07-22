package com.example.mixmate.ui.recipes

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mixmate.data.Cocktail

import com.example.mixmate.databinding.FragmentRecipeBinding
import com.example.mixmate.listeners.RecipeListOnClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeRecyclerViewAdapter(val values: List<Cocktail>,
    val recipeListOnClickListener: RecipeListOnClickListener)
    : RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRecipeBinding.inflate(
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
                    .resize(0,200)
                    .centerCrop()
                    .into(holder.recipeImageView)
            }
        }
        holder.binding.root.setOnClickListener{
            recipeListOnClickListener.onListItemClick(it, item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentRecipeBinding) :
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