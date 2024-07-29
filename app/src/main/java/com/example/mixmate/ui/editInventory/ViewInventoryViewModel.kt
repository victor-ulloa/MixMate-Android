package com.example.mixmate.ui.editInventory

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.repository.Supabase
import com.example.mixmate.ui.inventory.InventoryViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ViewInventoryViewModel(sharedViewModel: InventoryViewModel,
    private val dataStore: DataStore<Preferences>
): ViewModel() {
    private var selectedType: LiveData<Constants.InventoryItemType>? = null
    private val key: Preferences.Key<String>
    private val savedData: Flow<String>
    val supabase = Supabase()
    private val gson = Gson()

    val addedItemsLD: MutableLiveData<MutableList<InventoryItem>> = MutableLiveData()
    private val listType = object: TypeToken<List<InventoryItem>>() {}.type

    init {
        addedItemsLD.value = emptyList<InventoryItem>().toMutableList()
        selectedType = sharedViewModel.selectedType
        key = stringPreferencesKey("${Constants.ITEMS}_${selectedType?.value?.name}")
        savedData = dataStore.data.map { preferences ->
            preferences[key] ?: Constants.EMPTY_ARRAY_JSON
        }

        viewModelScope.launch { loadData() }
    }

    fun getList(): MutableList<InventoryItem> {
        return addedItemsLD.value!!
    }

    suspend fun loadData(){
        savedData.collect { value ->
            Log.d("ViewInventoryViewModel log", "read string: $value")

            val list = addedItemsLD.value!!
            list.clear()
            list.addAll(gson.fromJson(value, listType))
            addedItemsLD.value = list
        }
    }

    fun addItem(item: InventoryItem) {
        val list = addedItemsLD.value!!
        list.add(item)
        addedItemsLD.value = list

        viewModelScope.launch{
            saveData()
        }
    }
    fun removeItem(position: Int) {
        val list = addedItemsLD.value!!
        list.removeAt(position)
        addedItemsLD.value = list

        viewModelScope.launch{
            saveData()
        }
    }
    fun getSelectedType(): LiveData<Constants.InventoryItemType>? {
        return selectedType
    }
    suspend fun saveData() {
        val jsonStr = gson.toJson(addedItemsLD.value)
        dataStore.edit { preferences ->
            preferences[key] = jsonStr
        }
    }
}

class ViewInventoryViewModelFactory(
    private val sharedViewModel: InventoryViewModel,
    private val dataStore: DataStore<Preferences>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewInventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewInventoryViewModel(sharedViewModel, dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}