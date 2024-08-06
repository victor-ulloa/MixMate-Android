package com.example.mixmate.repository

import android.util.Log
import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.data.Recipe
import com.google.gson.Gson
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.util.UUID

class Supabase {

    companion object {
        const val cocktailsTable = "cocktails"
        const val inventoryListTable = "inventoryList"
        const val recipesTable = "recipes"
        const val columnId = "id"
        const val columnName = "name"
        const val columnType = "type"
        const val columnTags = "tags"
        const val columnIngredients = "ingredients"
        const val attrInventoryItem = "inventoryItem"

        val gson = Gson()
        private val supabase = createSupabaseClient(
            supabaseUrl = "https://npcddrdidmrwljkyxolk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5wY2RkcmRpZG1yd2xqa3l4b2xrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwNjU5NjQsImV4cCI6MjAzMzY0MTk2NH0.8G-WTrEcsepVzH6wb3_6t4B4DWPG0PzycfB_M0cpoJU"
        ) {
            install(Postgrest)
        }

        suspend fun getAllCocktails() : List<Cocktail>{
            return supabase.from(cocktailsTable).select().decodeList<Cocktail>().sortedBy { cocktail -> cocktail.name }
        }

        suspend fun getRecipeOfTheDay(): Cocktail {
            // NOTE: to change
            return getAllCocktails().last()
        }

        suspend fun getCocktailsByTags(tags: List<Constants.Tags>): List<Cocktail> {
            val tagsStr: MutableList<String> = arrayListOf()
            tags.forEach { tag -> tagsStr.add(tag.name) }
            val columns = Columns.raw("""
                *, $recipesTable!inner (
                    $columnTags
                )
            """.trimIndent())
            return supabase.from(cocktailsTable).select(
                columns = columns
            ) {
                filter {
                    contains("$recipesTable.$columnTags", tagsStr)
                }
            }.decodeList<Cocktail>()
        }

        suspend fun getCocktailsNameContains(text : String) : List<Cocktail>{
            return supabase.from(cocktailsTable).select{
                filter {
                    ilike(columnName, "%$text%")
                }
            }.decodeList<Cocktail>().sortedBy { cocktail -> cocktail.name }
        }

        suspend fun getCocktailsThatUseItem(item: InventoryItem):List<Cocktail> {
            val columns = Columns.raw("""
                *, $recipesTable!inner (
                    $columnIngredients
                )""".trimIndent())

            return supabase.from(cocktailsTable).select(
                columns = columns
            ) {
                filter {
                    eq("$recipesTable.$columnIngredients->0->$attrInventoryItem", gson.toJson(item))
                }
            }.decodeList<Cocktail>()
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
}