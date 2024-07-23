package com.example.mixmate.ui.editInventory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.databinding.FragmentViewInventoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class ModalBottomSheet(private val type: String): BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_item_to_inventory, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewInventoryViewModel()
        viewLifecycleOwner.lifecycleScope.launch {
            val data = viewModel.supabase.getInventoryItemsByType(type)

            val recyclerView = view.findViewById<RecyclerView>(R.id.to_add_items_rv)

            with (recyclerView){
                layoutManager = LinearLayoutManager(context)
                adapter= AddItemListRecyclerViewAdapter(data)
            }
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}


class ViewInventoryFragment : Fragment() {

    private var _binding: FragmentViewInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // add icon on top menu
        val menuHost = activity as MenuHost
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_add, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_add) {
                    val selectedType = requireArguments().getString("TYPE")!!

                    Log.d("In view inventory fragment", selectedType)

                    val bottomSheet = ModalBottomSheet(selectedType)
                    bottomSheet.show(activity!!.supportFragmentManager, ModalBottomSheet.TAG)
                }
                return false
            }
        })


        return root
    }


}