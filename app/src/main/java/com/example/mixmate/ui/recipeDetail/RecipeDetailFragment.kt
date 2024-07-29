package com.example.mixmate.ui.recipeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.FragmentRecipeDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val viewModel: RecipeDetailViewModel = ViewModelProvider(this)[RecipeDetailViewModel::class.java]

        binding.nameTextView.text = requireArguments().getString(Constants.NAME)

        viewLifecycleOwner.lifecycleScope.launch {
            Picasso.get()
                .load(requireArguments().getString(Constants.URL))
                .resize(600, 0)
                .centerCrop()
                .into(binding.cocktailImageView)
        }

        val recipeId = requireArguments().getString(Constants.RECIPE_ID)!!

        viewLifecycleOwner.lifecycleScope.launch {
            val recipe = viewModel.supabase.getRecipeById(recipeId)

            binding.descriptionTextView.text = recipe.description

            binding.stepsDetailtext.text = recipe.steps.joinToString(Constants.NEW_LINE)

            for (ingredient in recipe.ingredients){
                var currentString: String = binding.IngredientsDetailText.text.toString()
                currentString += "${ingredient.inventoryItem.name}: ${ingredient.amount} ${ingredient.unit}${Constants.NEW_LINE}"
                binding.IngredientsDetailText.text = currentString
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}