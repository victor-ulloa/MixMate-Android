package com.example.mixmate.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mixmate.data.Cocktail
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class CocktailRepository {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://npcddrdidmrwljkyxolk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5wY2RkcmRpZG1yd2xqa3l4b2xrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwNjU5NjQsImV4cCI6MjAzMzY0MTk2NH0.8G-WTrEcsepVzH6wb3_6t4B4DWPG0PzycfB_M0cpoJU"
    ) {
        install(Postgrest)
    }

    private var _allCocktails = MutableLiveData<List<Cocktail>>().apply { emptyList<Cocktail>() }
    val allCocktails : LiveData<List<Cocktail>> = _allCocktails
    suspend fun getAllCocktails() {
        _allCocktails.value = supabase.from("cocktails").select().decodeList<Cocktail>()
    }

    suspend fun returnAllCocktails() : List<Cocktail>{
        return supabase.from("cocktails").select().decodeList<Cocktail>()
    }

    private var _clickedViewId = MutableLiveData<Int>().apply { 0 }
    var clickedViewId : LiveData<Int> = _clickedViewId
    fun changeId(value : Int) {
        _clickedViewId.value = value
    }


}