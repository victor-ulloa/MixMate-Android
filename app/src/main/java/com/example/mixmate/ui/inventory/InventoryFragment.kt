package com.example.mixmate.ui.inventory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mixmate.R
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
        val inventoryViewModel =
            ViewModelProvider(this).get(InventoryViewModel::class.java)

        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val ingredientTypeConstraintLayout = binding.IngredientTypeConstraintLayout

        val openInventory = View.OnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.action_inventory_to_edit_inventory)
        }

        ingredientTypeConstraintLayout.children.forEach {
            Log.d("In Inventory Fragment", resources.getResourceName(it.id))
            it.setOnClickListener(openInventory)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}