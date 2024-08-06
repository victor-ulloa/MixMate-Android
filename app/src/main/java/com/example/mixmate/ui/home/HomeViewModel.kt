package com.example.mixmate.ui.home

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.repository.Supabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(private val dataStore: DataStore<Preferences>): ViewModel() {
    companion object{
        val LOG_TAG = "HomeViewModel log"
    }

    private val gson = Gson()
    private val listType = object: TypeToken<List<InventoryItem>>() {}.type
    private val savedItems: MutableList<InventoryItem> = arrayListOf()
    fun inventoryHasItems(): Boolean {
        return savedItems.isNotEmpty()
    }

    val recipeOfTheDay: MutableLiveData<Cocktail> = MutableLiveData()

    init {
        viewModelScope.launch {
            recipeOfTheDay.value = Supabase.getRecipeOfTheDay()
        }
    }

    val recList1: MutableLiveData<List<Cocktail>> = MutableLiveData(arrayListOf())
    val listTitle1: MutableLiveData<String> = MutableLiveData("")
    fun setRecList1(newList: List<Cocktail>) {
        recList1.value = newList
    }
    val recList2: MutableLiveData<List<Cocktail>> = MutableLiveData(arrayListOf())
    val listTitle2: MutableLiveData<String> = MutableLiveData("")
    fun setRecList2(newList: List<Cocktail>) {
        recList2.value = newList
    }

    suspend fun getRecipesBasedOnAllInventory() {
        val recList: MutableList<Cocktail> = emptyList<Cocktail>().toMutableList()
        runBlocking {
            for (item in savedItems) {
                val retrievedList = Supabase.getCocktailsThatUseItem(item)
                for (recipe in retrievedList) {
                    if (!recList.contains(recipe)){ recList.add(recipe) }
                }
            }
        }
        recList1.value = recList
        listTitle1.value = "Based on your inventory"
    }

    private suspend fun updateSavedItemsList() {
        savedItems.clear()
        Log.d(LOG_TAG, "${enumValues<Constants.InventoryItemType>()}")
        readDatabase(buildKeyList()).collect { value ->
            savedItems.addAll(gson.fromJson(value, listType))
        }
    }

    private fun readDatabase(keys: List<Preferences. Key<String>>): Flow<String> {
        var result = ""
        return dataStore.data.map { preferences ->
            for (key in keys) {
                result += preferences[key] ?: Constants.EMPTY_ARRAY_JSON
            }
            result
        }
    }
    private fun buildKeyList(): List<Preferences.Key<String>> {
        val list: MutableList<Preferences.Key<String>> = arrayListOf()
        for (type in enumValues<Constants.InventoryItemType>()) {
            list.add(stringPreferencesKey("${Constants.ITEMS}_${type.name}"))
        }
        return list
    }
}

class HomeViewModelFactory(private val dataStore: DataStore<Preferences>): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}