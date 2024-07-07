package com.example.mixmate

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mixmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_inventory, R.id.navigation_recipes, R.id.navigation_profile
            )
        )

        // hide navigation bar while editing inventory
        val destinationOnChangedListener = NavController.OnDestinationChangedListener {
                _, destination, _ ->
            run {
                when (destination.id) {
                    R.id.navigation_edit_inventory,
                    R.id.navigation_recipe_detail,
                                                    -> navView.visibility = View.GONE
                    else -> navView.visibility = View.VISIBLE
                }
            }
        }
        navController.addOnDestinationChangedListener(destinationOnChangedListener)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack()

        return super.onSupportNavigateUp()
    }
}