package com.example.mixmate.listeners

import android.view.View
import com.example.mixmate.data.Cocktail

interface RecipeListOnClickListener {
    fun onListItemClick(view : View, cocktail: Cocktail)
}