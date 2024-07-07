package com.example.mixmate.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mixmate.data.Cocktail
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class CocktailRepository {

    companion object cocktailsTable {
        val tableName = "cocktails"
        val columnId = "id"
        val columnName = "name"
        val columnShortDesc = "shortDescription"
        val columnImageName = "imageName"
    }


    private val supabase = createSupabaseClient(
        supabaseUrl = "https://npcddrdidmrwljkyxolk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5wY2RkcmRpZG1yd2xqa3l4b2xrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwNjU5NjQsImV4cCI6MjAzMzY0MTk2NH0.8G-WTrEcsepVzH6wb3_6t4B4DWPG0PzycfB_M0cpoJU"
    ) {
        install(Postgrest)
    }

    suspend fun returnAllCocktails() : List<Cocktail>{
        return supabase.from(tableName).select().decodeList<Cocktail>()
    }

    suspend fun getCocktailsNameContains(text : String) : List<Cocktail>{
        return supabase.from(tableName).select{
            filter {
                ilike(columnName, "%$text%")
            }
        }.decodeList<Cocktail>()
    }

}