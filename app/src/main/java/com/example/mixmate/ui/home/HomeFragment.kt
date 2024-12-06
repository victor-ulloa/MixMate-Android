package com.example.mixmate.ui.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
import com.example.mixmate.databinding.FragmentHomeBinding
import com.example.mixmate.listeners.RecipeListOnClickListener
import com.example.mixmate.repository.Supabase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.SAVED_ITEMS)

class HomeFragment : Fragment(), RecipeListOnClickListener {
    companion object{
        val LOG_TAG = "HomeFragment log"
    }

    private val CHANNEL_ID = "MIXMATE_CHANNEL"
    private var NOTIFICATION_ID = 0
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val listener: RecipeListOnClickListener = this

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter1: RecipeCarouselRecyclerViewAdapter
    private lateinit var adapter2: RecipeCarouselRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this,
            HomeViewModelFactory(requireContext())
        )[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.listTitle1.value = "drinks for summer"
        lifecycleScope.launch {
            homeViewModel.setRecList1(Supabase.getCocktailsByTags(arrayListOf(Constants.Tags.summer)))
        }
        homeViewModel.listTitle2.value = "alcohol free"
        lifecycleScope.launch {
            homeViewModel.setRecList2(Supabase.getCocktailsByTags(arrayListOf(Constants.Tags.nonAlcoholic)))
        }
        adapter1 = RecipeCarouselRecyclerViewAdapter(homeViewModel.recList1.value!!, listener)
        adapter2 = RecipeCarouselRecyclerViewAdapter(homeViewModel.recList2.value!!, listener)
        homeViewModel.recList1.observe(viewLifecycleOwner) { newValue ->
            adapter1.setData(newValue)
        }
        homeViewModel.recList2.observe(viewLifecycleOwner) { newValue ->
            adapter2.setData(newValue)
        }
        homeViewModel.listTitle1.observe(viewLifecycleOwner) {newValue ->
            binding.recipesListTitle1.text = newValue
        }
        homeViewModel.listTitle2.observe(viewLifecycleOwner) {newValue ->
            binding.recipesListTitle2.text = newValue
        }

        homeViewModel.recipeOfTheDay.observe(viewLifecycleOwner) { value ->
            lifecycleScope.launch {
                Picasso.get()
                    .load(value.imageURL)
                    .into(binding.featuredRecipeView)
            }
            binding.featuredRecipeText.text = value.name
            binding.featuredRecipeView.setOnClickListener {
                onListItemClick(binding.featuredRecipeView, value)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listRv1 :RecyclerView = binding.recipesListRv1
        with(listRv1){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = adapter1
        }
        val listRv2 :RecyclerView = binding.recipesListRv2
        with(listRv2){
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = adapter2
        }

        binding.generateRecommendationButton.setOnClickListener {
            if (homeViewModel.inventoryHasItems()) {
                homeViewModel.getRecipesBasedOnAllInventory()
            }
            else {
                Toast.makeText(context, "your inventory is empty :(", Toast.LENGTH_SHORT).show()
            }
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
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_TAG, "resumed")

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Reminder: Your inventory is empty!")
            .setContentText("Remember to add items to your inventory before trying to generate recommendations!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel()

        if (context != null) {
            lifecycleScope.launch {
                homeViewModel.fetchSavedItems(requireContext())
                if (!homeViewModel.inventoryHasItems()) {
                    Log.i(LOG_TAG, "no inventory items found")
                    with(NotificationManagerCompat.from(requireContext())) {
                        if (ActivityCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@with
                        }
                        notify(++NOTIFICATION_ID, builder.build())
                    }
                }
            }
        }
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

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}