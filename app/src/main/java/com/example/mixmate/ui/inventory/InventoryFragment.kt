package com.example.mixmate.ui.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.Constants
import com.example.mixmate.databinding.FragmentInventoryBinding
import com.example.mixmate.listeners.InventoryOnClickListener

class InventoryFragment : Fragment(), InventoryOnClickListener {

    private var _binding: FragmentInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val onClickListener = this
    private lateinit var viewModel: InventoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]

        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })

        val rv: RecyclerView = binding.inventoryRv
        with(rv){
            layoutManager = LinearLayoutManager(context)
            adapter = InventoryRecyclerViewAdapter(enumValues<Constants.InventoryItemType>(), onClickListener)
        }

        return root
    }

    override fun onClick(view: View) {
        viewModel.setSelectedType(Constants.InventoryItemType.valueOf(view.tag.toString()))
        Navigation.findNavController(view).navigate(R.id.action_inventory_to_view_inventory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}