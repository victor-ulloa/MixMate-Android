package com.example.mixmate.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Constants
import com.example.mixmate.repository.Supabase

class InventoryViewModel : ViewModel() {

    val supabase = Supabase()
}