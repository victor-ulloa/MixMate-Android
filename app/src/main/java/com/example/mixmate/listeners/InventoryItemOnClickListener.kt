package com.example.mixmate.listeners

import com.example.mixmate.data.InventoryItem

interface InventoryItemOnClickListener {
    fun onListItemClick(item: InventoryItem)
}