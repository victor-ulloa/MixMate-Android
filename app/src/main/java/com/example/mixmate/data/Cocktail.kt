package com.example.mixmate.data

import kotlinx.serialization.Serializable

@Serializable
data class Cocktail(val id: Int,
                    val name : String,
                    val shortDescription : String,
                    val imageName : String,
                    val imageURL : String,)
