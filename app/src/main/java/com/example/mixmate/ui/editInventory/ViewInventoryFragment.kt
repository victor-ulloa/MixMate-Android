package com.example.mixmate.ui.editInventory

import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.Constants
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.listeners.InventoryItemOnClickListener
import com.example.mixmate.ui.inventory.InventoryViewModel
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.SAVED_ITEMS)

class ViewInventoryFragment: Fragment(), InventoryItemOnClickListener {

    private lateinit var addedItemsRvAdapter: AddedItemListRecyclerViewAdapter
    private lateinit var viewModel: ViewInventoryViewModel
    private lateinit var addedItemsRv: RecyclerView

    private var oldListCount: Int = 0

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
        viewModel = ViewModelProvider(this,
            ViewInventoryViewModelFactory(sharedModel, requireContext().dataStore)
        )[ViewInventoryViewModel::class.java]

        val onClickListener: InventoryItemOnClickListener = this

        // init added items rv
        addedItemsRv = view.findViewById(R.id.added_items_rv)
        addedItemsRvAdapter = AddedItemListRecyclerViewAdapter(viewModel.getList())
        with (addedItemsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = addedItemsRvAdapter
        }

        // recycler view render update
        oldListCount = viewModel.getList().count()
        viewModel.addedItemsLD.observe(viewLifecycleOwner){ newList ->
            val newListCount = newList.count()
            Log.d("ViewInventoryFragment log", "$newListCount vs $oldListCount")

            if (oldListCount == 0) { // first time render full list as the list was initialized to be empty
                addedItemsRvAdapter.notifyItemRangeChanged(0, newListCount)
            }
            else if(newList.count() - oldListCount == 1) {
                addedItemsRvAdapter.notifyItemInserted(newList.lastIndex)
            }
            else {
                addedItemsRvAdapter.notifyDataSetChanged()
            }

            oldListCount = newList.count()
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
                    val selectedType = viewModel.getSelectedType()!!.value!!
                    if (isAdded) {
                        lifecycleScope.launch {
                            val data = viewModel.supabase.getInventoryItemsByType(selectedType)
                            Log.d("ViewInventoryFragment log", "retrieved ${data.count()} items for type ${selectedType.name}")
                            val bottomSheet = ModalBottomSheet(data, onClickListener)
                            bottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
                        }
                    }
                }
                return false
            }
        })

        // swipe action
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                Log.d("ViewInventoryFragment log", "removing item at $position")
                viewModel.removeItem(position)
            }
        }
        // attach the ItemTouchHelper to the RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(addedItemsRv)
    }

    override fun onListItemClick(item: InventoryItem) {
        viewModel.addItem(item)
    }
}
