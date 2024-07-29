package com.example.mixmate.repository

import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.data.Recipe
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import java.util.UUID

class Supabase {

    companion object {
        val cocktailsTable = "cocktails"
        val inventoryListTable = "inventoryList"
        val recipesTable = "recipes"
        val columnId = "id"
        val columnName = "name"
        val columnType = "type"
    }


    private val supabase = createSupabaseClient(
        supabaseUrl = "https://npcddrdidmrwljkyxolk.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5wY2RkcmRpZG1yd2xqa3l4b2xrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwNjU5NjQsImV4cCI6MjAzMzY0MTk2NH0.8G-WTrEcsepVzH6wb3_6t4B4DWPG0PzycfB_M0cpoJU"
    ) {
        install(Postgrest)
    }

    suspend fun getAllCocktails() : List<Cocktail>{
        return supabase.from(cocktailsTable).select().decodeList<Cocktail>().sortedBy { cocktail -> cocktail.name }
    }

    suspend fun getCocktailsNameContains(text : String) : List<Cocktail>{
        return supabase.from(cocktailsTable).select{
            filter {
                ilike(columnName, "%$text%")
            }
        }.decodeList<Cocktail>().sortedBy { cocktail -> cocktail.name }
    }

    suspend fun getInventoryItemsByType(type: Constants.InventoryItemType): List<InventoryItem> {
        return supabase.from(inventoryListTable).select {
            filter {
                eq(columnType, type.name)
            }
        }.decodeList<InventoryItem>()
    }

    suspend fun getRecipeById(recipeId: String): Recipe {
        return supabase.from(recipesTable).select {
            filter {
                eq(columnId, UUID.fromString(recipeId))
            }
        }.decodeList<Recipe>().first()
    }
}