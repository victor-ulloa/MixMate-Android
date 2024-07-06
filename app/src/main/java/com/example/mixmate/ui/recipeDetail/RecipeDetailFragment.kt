package com.example.mixmate.ui.recipeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val root: View = binding.root

            viewLifecycleOwner.lifecycleScope.launch {
                Picasso.get()
                    .load(requireArguments().getString("URL"))
                    .resize(600, 0)
                    .centerCrop()
                    .into(binding.cocktailImageView)
            }
            binding.cocktailNameText.text = requireArguments().getString("NAME")
            binding.shortDescText.text = requireArguments().getString("DESC")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}