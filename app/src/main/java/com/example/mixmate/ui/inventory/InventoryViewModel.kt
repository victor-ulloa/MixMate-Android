package com.example.mixmate.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Constants
import com.example.mixmate.repository.Supabase

class InventoryViewModel : ViewModel() {
    internal val selectedType = MutableLiveData<String>()
    fun getSelectedType(): LiveData<String> {
        return selectedType
    }
    fun setSelectedType(type:String) {
        selectedType.value = type
    }
}