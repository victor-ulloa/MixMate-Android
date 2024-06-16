package com.example.mixmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mixmate.R
import com.example.mixmate.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        viewLifecycleOwner.lifecycleScope.launch {
            var imageView : ImageView = binding.themedRecipe1
            Picasso.get()
                .load(getString(R.string.espresso_martini))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe2
            Picasso.get()
                .load(getString(R.string.old_fashioned))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe3
            Picasso.get()
                .load(getString(R.string.margarita_blue))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe21
            Picasso.get()
                .load(getString(R.string.whiskey_sour))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe22
            Picasso.get()
                .load(getString(R.string.manhattan))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe23
            Picasso.get()
                .load(getString(R.string.mojito))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.themedRecipe24
            Picasso.get()
                .load(getString(R.string.piña_colada))
                .resize(150,200)
                .centerCrop()
                .into(imageView)

            imageView = binding.featuredRecipeView
            Picasso.get()
                .load(getString(R.string.espresso_martini))
                .into(imageView)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}