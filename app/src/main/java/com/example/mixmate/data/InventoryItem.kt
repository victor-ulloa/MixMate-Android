package com.example.mixmate.data

import kotlinx.serialization.Serializable

@Serializable
data class InventoryItem(
    //val id: Int,
    val name: String,
    val type: Constants.InventoryItemType,
)

/**
 * NOTE: sample jsonb format saved on supabase; will save items local in the same format for future integration
 *"{
 * "items":[
 *           {"name":"Absinthe","type":"spirit"},
 *           {"name":"Canadian Whiskey","type":"spirit"}
 *         ]
 * }"
 */