package com.example.mixmate.ui.recipeDetail

import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Ingredient
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.repository.Supabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeDetailViewModel(): ViewModel() {

    val supabase = Supabase()
    private val gson = Gson()

    private val listType = object: TypeToken<Array<Ingredient>>() {}.type

    fun getIngredientList(jsonStr: String): Array<Ingredient>{
        return gson.fromJson(jsonStr, listType)
    }
}