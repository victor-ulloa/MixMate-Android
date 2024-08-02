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
import com.example.mixmate.data.Constants
import com.example.mixmate.listeners.RecipeListOnClickListener
import kotlinx.coroutines.launch

class RecipeFragment : Fragment(), RecipeListOnClickListener {

    private val columnCount = 2
    private val listener: RecipeListOnClickListener = this

    private lateinit var viewModel: RecipeViewModel
    private lateinit var recyclerView: RecyclerView
    private val rvAdapter: RecipeRecyclerViewAdapter = RecipeRecyclerViewAdapter(emptyList<Cocktail>().toMutableList(), listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, RecipeViewModelFactory())[RecipeViewModel::class.java]
        lifecycleScope.launch {
            viewModel.loadAll()
            Log.d("RecipeFragment log","all loaded: ${viewModel.recipesLiveData.value!!.count()}")
        }

        // init recycler view
        recyclerView = view.findViewById(R.id.recipesRv)
        with(recyclerView){
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = rvAdapter
        }

        viewModel.recipesLiveData.observe(viewLifecycleOwner) { newList ->
            rvAdapter.setData(newList)
        }

        // search view in menu bar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu. menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_search){
                    Log.d("recipe fragment w/ recycler view", "search menu clicked")
                    val searchView = menuItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            filter(query.toString())
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            filter(newText.toString())
                            return false
                            }
                    })
                }
                return false
            }
        })
    }

    fun filter(text: String){
        lifecycleScope.launch {
            viewModel.filter(text)
        }
    }

    override fun onListItemClick(view: View, cocktail: Cocktail) {
        val bundle = bundleOf(
            Pair(Constants.URL, cocktail.imageURL),
            Pair(Constants.NAME,cocktail.name),
            Pair(Constants.RECIPE_ID, cocktail.recipe.toString())
        )

        Navigation.findNavController(view).navigate(R.id.action_view_recipes_to_recipe_detail, bundle)
    }
}