package com.example.mixmate.ui.home

import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Cocktail
import com.example.mixmate.repository.Supabase

class HomeViewModel : ViewModel() {
    val supabase = Supabase()
}