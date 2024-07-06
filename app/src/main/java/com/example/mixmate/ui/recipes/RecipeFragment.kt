package com.example.mixmate.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mixmate.R
import com.example.mixmate.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {

    private val columnCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        val recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        Log.d("view recipes","attempt to retrieve all items")

        viewLifecycleOwner.lifecycleScope.launch {
            val allRecipes = recipeViewModel.cocktailRepository.returnAllCocktails()

            Log.d("view recipes","retrieved " + allRecipes.size + " items")

            // Set the adapter
            if (view is RecyclerView) {
                with(view) {
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = RecipeRecyclerViewAdapter(allRecipes)
                }
            }
        }

        return view
    }

}