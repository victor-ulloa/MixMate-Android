package com.example.mixmate.ui.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mixmate.R
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)

        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuHost = activity as MenuHost
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })

        binding.itemCategoryLayout.children.forEach { it ->
            val selectedType = it.tag.toString()
            Log.d("In Inventory Fragment", selectedType)
            it.setOnClickListener{
                inventoryViewModel.setSelectedType(it.tag.toString())
                Navigation.findNavController(it).navigate(R.id.action_inventory_to_view_inventory)
            }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}