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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.listeners.InventoryItemOnClickListener
import com.example.mixmate.ui.inventory.InventoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class ViewInventoryFragment : Fragment(), InventoryItemOnClickListener {

    private val addedItems: MutableList<InventoryItem> = emptyList<InventoryItem>().toMutableList()
    private val addedItemsRvAdapter: AddedItemListRecyclerViewAdapter = AddedItemListRecyclerViewAdapter(addedItems)

    private lateinit var addedItemsRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_view_inventory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedModel: InventoryViewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]
        val viewModel = ViewInventoryViewModel(sharedModel)
        val onClickListener: InventoryItemOnClickListener = this

        // init added items rv
        addedItemsRv = view.findViewById(R.id.added_items_rv)
        with (addedItemsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = addedItemsRvAdapter
        }

        // add icon on top menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_add, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_add) {
                    val selectedType =
                        if (viewModel.getSelectedType() != null)
                            viewModel.getSelectedType()!!.value
                        else ""

                    if (selectedType != null) {
                        Log.d("In view inventory fragment", selectedType)
                        val bottomSheet = ModalBottomSheet(selectedType, viewModel, onClickListener)
                        bottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
                    }
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onListItemClick(item: InventoryItem) {
        addedItems.add(item)
        addedItemsRvAdapter.notifyItemChanged(addedItems.lastIndex)
    }

    override fun onDestroy() {
        // TO-DO: save to local

        super.onDestroy()
    }
}