package com.example.tastynotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("recipe_id")
    val recipeId: Int? = null,
    @SerialName("product_id")
    val productId: Int? = null,
    val quantity: Double,
    @SerialName("products")
    val product: Product? = null,
)