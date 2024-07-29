package com.example.mixmate.ui.editInventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mixmate.R
import com.example.mixmate.data.InventoryItem
import com.example.mixmate.listeners.InventoryItemOnClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet(private val data: List<InventoryItem>,
                       private val onClickListener: InventoryItemOnClickListener): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_item_to_inventory, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // retrieve data and render recycler view of to add items
        val recyclerView = view.findViewById<RecyclerView>(R.id.to_add_items_rv)
        with (recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = AddItemListRecyclerViewAdapter(data, onClickListener)
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}