package com.example.mixmate.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(val context: Context): ViewModel() {
    companion object{
        val LOG_TAG = "HomeViewModel log"
    }

    private val gson = Gson()
    private val listType = object: TypeToken<List<InventoryItem>>() {}.type
    private val savedItems: MutableList<InventoryItem> = arrayListOf()
    fun inventoryHasItems(): Boolean {
        return savedItems.isNotEmpty()
    }
    fun getSavedItems(): MutableList<InventoryItem> {
        return savedItems
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

    fun getRecipesBasedOnAllInventory() {
        val recList: MutableList<Cocktail> = emptyList<Cocktail>().toMutableList()
        for (item in savedItems) {
            viewModelScope.launch {
                try {
                    val retrievedList = Supabase.getCocktailsThatUseItem(item)
                    Log.d(LOG_TAG, "for $item: retrieved $retrievedList")
                    for (recipe in retrievedList) {
                        if (!recList.contains(recipe)) {
                            recList.add(recipe)
                        }
                    }
                    Log.d(LOG_TAG, "$recList")
                    recList1.value = recList
                    listTitle1.value = "Based on your inventory"

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun fetchSavedItems(context: Context) {
        savedItems.clear()
        Log.d(LOG_TAG, "enum values: ${enumValues<Constants.InventoryItemType>()}")
        val keyList = buildKeyList()
        val preferencesValues = getPreferencesValues(context, keyList)

        for (key in keyList) {
            val retrievedValue :String = preferencesValues[key.toString()] ?: ""
            Log.d(LOG_TAG, "$key, $retrievedValue")
            if (retrievedValue.isNotEmpty()){
                savedItems.addAll(gson.fromJson(retrievedValue, listType))
            }
        }
    }

    private suspend fun getPreferencesValues(context: Context, keys: List<Preferences. Key<String>>): Map<String, String?> {
        val preferencesFlow = context.dataStore.data.map { preferences ->
            keys.associate { key ->
                key.name to preferences[key]
            }
        }
        return preferencesFlow.first()
    }

    private fun buildKeyList(): List<Preferences.Key<String>> {
        val list: MutableList<Preferences.Key<String>> = arrayListOf()
        for (type in enumValues<Constants.InventoryItemType>()) {
            list.add(stringPreferencesKey("${Constants.ITEMS}_${type.name}"))
        }
        return list
    }
}

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}