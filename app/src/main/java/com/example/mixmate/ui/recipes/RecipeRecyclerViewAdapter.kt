package com.example.mixmate.ui.recipes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mixmate.data.Cocktail

import com.example.mixmate.databinding.FragmentRecipeBinding
import com.squareup.picasso.Picasso

class RecipeRecyclerViewAdapter(private val values: List<Cocktail>)
    : RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>() {

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

        holder.recipeView.setOnClickListener { TODO("Not yet implemented") }

        Picasso.get()
            .load(item.imageURL)
            .into(holder.recipeImageView)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentRecipeBinding) :
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