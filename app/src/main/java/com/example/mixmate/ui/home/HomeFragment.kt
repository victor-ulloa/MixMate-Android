package com.example.mixmate.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.children
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.Cocktail
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.databinding.FragmentHomeBinding
import com.example.mixmate.listeners.RecipeListOnClickListener
import com.example.mixmate.repository.Supabase
import com.example.mixmate.ui.recipes.RecipeRecyclerViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.SAVED_ITEMS)

class HomeFragment : Fragment(), RecipeListOnClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val listener: RecipeListOnClickListener = this

    private val adapter1: RecipeCarouselRecyclerViewAdapter = RecipeCarouselRecyclerViewAdapter(emptyList<Cocktail>().toMutableList(), listener)
    private val adapter2: RecipeCarouselRecyclerViewAdapter = RecipeCarouselRecyclerViewAdapter(emptyList<Cocktail>().toMutableList(), listener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this,
            HomeViewModelFactory(requireContext().dataStore)
        )[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listRv1 :RecyclerView = binding.recipesListRv1
        with(listRv1){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = adapter1
        }
        homeViewModel.recList1.observe(viewLifecycleOwner) { newValue ->
            adapter1.setData(newValue)
        }
        lifecycleScope.launch {
            homeViewModel.setRecList1(Supabase.getCocktailsByTags(arrayListOf(Constants.Tags.summer)))
            binding.recipesListTitle1.text = "drinks for summer"
        }

        val listRv2 :RecyclerView = binding.recipesListRv2
        with(listRv2){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = adapter2
        }
        homeViewModel.recList2.observe(viewLifecycleOwner) { newValue ->
            adapter2.setData(newValue)
        }
        lifecycleScope.launch {
            homeViewModel.setRecList2(Supabase.getCocktailsByTags(arrayListOf(Constants.Tags.refreshing)))
            binding.recipesListTitle2.text = "refreshing!"
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })
        return root
    }

    override fun onListItemClick(view: View, cocktail: Cocktail) {
        val bundle = bundleOf(
            Pair(Constants.URL, cocktail.imageURL),
            Pair(Constants.NAME,cocktail.name),
            Pair(Constants.RECIPE_ID, cocktail.recipe.toString())
        )

        Navigation.findNavController(view).navigate(R.id.action_home_to_recipe_detail, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}