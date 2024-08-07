package com.example.mixmate.ui.recipes

import android.graphics.drawable.Drawable.ConstantState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.data.Recipe
import com.example.mixmate.repository.Supabase

class RecipeViewModel : ViewModel() {

    private val _selectedTags :MutableList<Constants.Tags> = arrayListOf()
    val selectedTagsLiveData: MutableLiveData<MutableList<Constants.Tags>> = MutableLiveData()
    fun selectedTagsContains(tag: Constants.Tags) :Boolean {
        return _selectedTags.contains(tag)
    }
    fun addTag(tag: Constants.Tags) {
        _selectedTags.add(tag)
        selectedTagsLiveData.value = _selectedTags
    }
    fun removeTag(tag: Constants.Tags) {
        _selectedTags.remove(tag)
        selectedTagsLiveData.value = _selectedTags
    }

    val recipesLiveData: MutableLiveData<List<Cocktail>> = MutableLiveData(arrayListOf())

    suspend fun loadAll(){
        recipesLiveData.value = Supabase.getAllCocktails()
    }

    suspend fun filter(keyword: String) {
        recipesLiveData.value = Supabase.getCocktailsNameContains(keyword)
    }

    suspend fun filterByTags() {
        if (_selectedTags.isEmpty()) {
            loadAll()
        }
        else {
            recipesLiveData.value = Supabase.getCocktailsByTags(_selectedTags)
        }
    }

}