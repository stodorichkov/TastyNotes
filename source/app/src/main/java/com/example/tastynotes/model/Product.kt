package com.example.tastynotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int? = null,
    val name: String,
    val quantity: String,
    @SerialName("recipe_id")
    var recipeId: Int? = null
)