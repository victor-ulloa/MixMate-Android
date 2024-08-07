package com.example.mixmate.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class InventoryItem(
    @Serializable(with = Constants.UUIDSerializer::class)
    val id: UUID,
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