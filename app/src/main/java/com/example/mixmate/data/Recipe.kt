package com.example.mixmate.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Recipe (
    @Serializable(with = Constants.UUIDSerializer::class)
    val id: UUID,

    val description: String,
    val steps: List<String>,
    val calories: Double,
    val alcoholContent: Double,
    val complexity: Int,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
)