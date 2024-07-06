package com.example.mixmate.ui.recipeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mixmate.data.Cocktail
import com.example.mixmate.databinding.FragmentRecipeDetailBinding
import com.example.mixmate.ui.home.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding?=null
    private val binding get() = _binding!!

    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val root: View = binding.root

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        homeViewModel.clickedViewId.observe(viewLifecycleOwner) {
            val index: Int = it
            Log.d(
                "RecipeDetailFragment communication", "accessed Cocktail index is $index"
            )
            val cocktail: Cocktail = homeViewModel.allCocktailsLocal[index]

            viewLifecycleOwner.lifecycleScope.launch {
                Picasso.get()
                    .load(cocktail.imageURL)
                    .resize(600, 0)
                    .centerCrop()
                    .into(binding.cocktailImageView)
            }
            binding.cocktailNameText.text = cocktail.name
            binding.shortDescText.text = cocktail.shortDescription
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}