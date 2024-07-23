package com.example.mixmate.ui.editInventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Constants
import com.example.mixmate.repository.Supabase
import com.example.mixmate.ui.inventory.InventoryViewModel

class ViewInventoryViewModel(sharedViewModel: InventoryViewModel): ViewModel() {

    val supabase = Supabase()

    private var selectedType: LiveData<String>? = null
    init {
        selectedType = sharedViewModel.selectedType
    }
    fun getSelectedType(): LiveData<String>? {
        return selectedType
    }

}