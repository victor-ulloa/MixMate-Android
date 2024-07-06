package com.example.mixmate.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mixmate.R
import com.example.mixmate.data.Cocktail
import com.example.mixmate.listeners.RecipeListOnClickListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeFragment : Fragment(), RecipeListOnClickListener {

    private val columnCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        val recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        val listener : RecipeListOnClickListener = this

        // retrieve data from db and display them
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
                    adapter = RecipeRecyclerViewAdapter(allRecipes, listener)
                }
            }
        }

        // search view in menu bar
        val menuHost = activity as MenuHost
        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_search){
                    val searchView = menuItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            var querySuccess = false
                            Log.d("search view in menu",query.toString())

                            runBlocking {
                                launch {
                                    val filtered = recipeViewModel.cocktailRepository.getCocktailsNameContains(query.toString())
                                    Log.d("search view in menu", "retrieved " + filtered.size + " items")
                                    if (view is RecyclerView) {
                                        with(view) {
                                            adapter = RecipeRecyclerViewAdapter(filtered, listener)
                                            querySuccess = true
                                        }
                                    }
                                }
                            }
                            return querySuccess
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var querySuccess = false
                            Log.d("search view in menu",newText.toString())

                            runBlocking {
                                launch {
                                    val filtered = recipeViewModel.cocktailRepository.getCocktailsNameContains(newText.toString())
                                    Log.d("search view in menu", "retrieved " + filtered.size + " items")

                                    if (view is RecyclerView) {
                                        with(view) {
                                            adapter = RecipeRecyclerViewAdapter(filtered, listener)
                                            querySuccess = true
                                        }
                                    }
                                }
                            }
                            return querySuccess
                        }
                    })
                    return true
                }
                return false
            }
        })
        return view
    }

    override fun onListItemClick(view: View, cocktail: Cocktail) {
        val bundle = bundleOf(
            Pair("URL", cocktail.imageURL),
            Pair("NAME", cocktail.name),
            Pair("DESC", cocktail.shortDescription)
        )

        Navigation.findNavController(view).navigate(R.id.action_view_recipes_to_recipe_detail, bundle)
    }

}