package com.example.mixmate.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.children

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mixmate.R
import com.example.mixmate.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val recipesViewIds = intArrayOf(
        R.id.featuredRecipeView,
        R.id.themedRecipe1,
        R.id.themedRecipe2,
        R.id.themedRecipe3,
        R.id.themedRecipe4,
        R.id.themedRecipe5,
        R.id.themedRecipe6,
        R.id.themedRecipe7,
        R.id.themedRecipe8,)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            Picasso.get()
                .load(homeViewModel.allCocktailsLocal[0].imageURL)
                .resize(0,800)
                .centerCrop()
                .into(binding.featuredRecipeView)

            var id = 1
            binding.horizontalLinearLayout1.children.forEach {
                val imageView = it as ImageView
                Picasso.get()
                    .load(homeViewModel.allCocktailsLocal[id].imageURL)
                    .resize(150,200)
                    .centerCrop()
                    .into(imageView)
                id++
            }
            binding.horizontalLinearLayout2.children.forEach {
                val imageView = it as ImageView
                Picasso.get()
                    .load(homeViewModel.allCocktailsLocal[id].imageURL)
                    .resize(150,200)
                    .centerCrop()
                    .into(imageView)
                id++
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        for (i in recipesViewIds.indices) {
            val imageView = view.findViewById<ImageView>(recipesViewIds[i])
            imageView.setOnClickListener { thisView ->

                val clickedCocktail = homeViewModel.allCocktailsLocal[recipesViewIds.indexOf(thisView.id)]

                val bundle = bundleOf(
                    Pair("URL", clickedCocktail.imageURL),
                    Pair("NAME", clickedCocktail.name),
                    Pair("DESC", clickedCocktail.shortDescription)
                )

                Navigation.findNavController(view).navigate(R.id.action_home_to_recipe_detail, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}