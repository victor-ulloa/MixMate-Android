package com.example.mixmate.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.FragmentViewRecipesBinding
import com.example.mixmate.listeners.TagOnClickListener
import io.ktor.utils.io.concurrent.shared

class ViewRecipesFragment : Fragment(), TagOnClickListener {
    companion object{
        val LOG_TAG = "ViewRecipesFragment log"
    }

    private var _binding: FragmentViewRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val listener: TagOnClickListener = this
    private lateinit var sharedModel : RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewRecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]

        val rv = binding.tagRv
        with (rv) {
            layoutManager = LinearLayoutManager(context,
                RecyclerView.HORIZONTAL,
                false)
            adapter = TagsRecyclerViewAdapter(listener)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTagClick(view: View, tag: Constants.Tags) {
        if (sharedModel.selectedTagsContains(tag)) {
            sharedModel.removeTag(tag)
        }
        else {
            sharedModel.addTag(tag)
        }

        Log.d(LOG_TAG, sharedModel.selectedTagsLiveData.value!!.toString())
    }
}