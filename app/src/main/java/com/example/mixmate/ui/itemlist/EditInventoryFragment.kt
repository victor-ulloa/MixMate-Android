package com.example.mixmate.ui.itemlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mixmate.databinding.FragmentEditInventoryBinding

class EditInventoryFragment : Fragment() {

    private var _binding: FragmentEditInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


}