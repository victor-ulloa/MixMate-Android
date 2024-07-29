package com.example.mixmate.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Cocktail(val id: Int,
                    val name : String,
                    val shortDescription : String,
                    val imageURL : String,
                    @Serializable(with = Constants.UUIDSerializer::class)
                    val recipe: UUID,)
