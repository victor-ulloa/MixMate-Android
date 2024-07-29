package com.example.mixmate.ui.recipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mixmate.data.Cocktail
import com.example.mixmate.repository.Supabase

class RecipeViewModel : ViewModel() {

    private val supabase = Supabase()

    val recipesLiveData: MutableLiveData<List<Cocktail>?> = MutableLiveData()

    init {
        recipesLiveData.value = emptyList()
    }

    suspend fun loadAll(){
        recipesLiveData.value = supabase.getAllCocktails()
    }

    suspend fun filter(keyword: String) {
        recipesLiveData.value = supabase.getCocktailsNameContains(keyword)
    }
}

class RecipeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}