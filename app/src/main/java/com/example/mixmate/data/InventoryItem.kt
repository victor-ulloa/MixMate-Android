package com.example.mixmate.data

import kotlinx.serialization.Serializable

@Serializable
data class InventoryItem(
    val id: Int,
    val name: String,
    val type: Constants.InventoryItemType,
)