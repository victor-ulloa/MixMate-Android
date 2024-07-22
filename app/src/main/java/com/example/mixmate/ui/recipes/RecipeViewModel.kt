package com.example.mixmate.ui.recipes

import androidx.lifecycle.ViewModel
import com.example.mixmate.repository.CocktailRepository

class RecipeViewModel : ViewModel() {

    val cocktailRepository = CocktailRepository()

}