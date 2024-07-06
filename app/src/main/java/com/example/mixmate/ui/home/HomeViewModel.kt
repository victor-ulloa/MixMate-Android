package com.example.mixmate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mixmate.data.Cocktail
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class HomeViewModel : ViewModel() {

    val allCocktailsLocal : Array<Cocktail> = arrayOf(
        Cocktail(2, "Frozen Margarita","Frozen Margarita Description","Frozen Margarita", "https://images.pexels.com/photos/7809757/pexels-photo-7809757.jpeg"),
        Cocktail(1, "Whiskey Sour","Whiskey Sour Description","Whiskey Sour", "https://images.pexels.com/photos/6542662/pexels-photo-6542662.jpeg"),
        Cocktail(3, "Vodka Martini","Vodka Martini Description","Vodka Martini", "https://images.pexels.com/photos/4786625/pexels-photo-4786625.jpeg"),
        Cocktail(4, "Mojito","Mojito Description","Mojito", "https://images.pexels.com/photos/4021983/pexels-photo-4021983.jpeg"),
        Cocktail(5, "Old-fashioned","Old-fashioned Description","Old-fashioned", "https://images.pexels.com/photos/19252758/pexels-photo-19252758/free-photo-of-whiskey-with-ice-cubes-and-orange-peel-in-lowball-glass.jpeg"),
        Cocktail(6, "Espresso Martini", "Espresso Martini Description","Espresso Martini", "https://images.pexels.com/photos/15082311/pexels-photo-15082311/free-photo-of-glass-of-espresso-martini-cocktail.jpeg" ),
        Cocktail(7, "Manhattan", "Manhattan Description", "Manhattan", "https://images.pexels.com/photos/7809770/pexels-photo-7809770.jpeg"),
        Cocktail(8, "Piña Colada", "Piña Colada Description", "Piña Colada", "https://images.pexels.com/photos/10986577/pexels-photo-10986577.jpeg"),
        Cocktail(9, "Mango Mojito", "Mango Mojito Description", "Mango Mojito", "https://images.pexels.com/photos/10836605/pexels-photo-10836605.jpeg"),
        Cocktail(10, "Jalapeno Vodka", "Jalapeno Vodka Description","Jalapeno Vodka","https://images.pexels.com/photos/4631022/pexels-photo-4631022.jpeg"),
        Cocktail(11,"Polish Honey Vodka", "Polish Honey Vodka Description", "Polish Honey Vodka", "https://images.pexels.com/photos/4631019/pexels-photo-4631019.jpeg")
    )

    private var _clickedViewId = MutableLiveData<Int>().apply { 0 }
    var clickedViewId : LiveData<Int> = _clickedViewId
    fun changeId(value : Int) {
        _clickedViewId.value = value
    }

    val supabase = createSupabaseClient(
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
}