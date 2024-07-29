package com.example.mixmate.data

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient (
    val unit: String,
    val amount: Double,
    val inventoryItem: InventoryItem
)
