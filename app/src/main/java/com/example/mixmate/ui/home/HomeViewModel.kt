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

class HomeViewModel(private val dataStore: DataStore<Preferences>): ViewModel() {

    private val gson = Gson()
    val recList1: MutableLiveData<MutableList<Cocktail>> = MutableLiveData(arrayListOf())
    fun setRecList1(newList: List<Cocktail>) {
        recList1.value = newList.toMutableList()
    }
    fun addToRecList1(newList: List<Cocktail>){
        recList1.value?.addAll(newList)
    }
    val recList2: MutableLiveData<MutableList<Cocktail>> = MutableLiveData(arrayListOf())
    fun setRecList2(newList: List<Cocktail>) {
        recList2.value = newList.toMutableList()
    }
    fun addToRecList2(newList: List<Cocktail>){
        recList2.value?.addAll(newList)
    }
    private val listType = object: TypeToken<List<InventoryItem>>() {}.type

    private suspend fun getRecipesWithItemType(type: Constants.InventoryItemType): List<Cocktail> {
        val key = stringPreferencesKey("${Constants.ITEMS}_${type.name}")
        val savedData = dataStore.data.map { preferences ->
            preferences[key] ?: Constants.EMPTY_ARRAY_JSON }
        val itemList: MutableList<InventoryItem> = arrayListOf()
        savedData.collect { value ->
            itemList.addAll(gson.fromJson(value, listType))
        }
        val recList: MutableList<Cocktail> = arrayListOf()
        for (item in itemList){
            recList.addAll(Supabase.getCocktailsThatUseItem(item))
        }

        return recList
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