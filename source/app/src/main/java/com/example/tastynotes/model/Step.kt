package com.example.tastynotes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val id: Int? = null,
    val text: String,
    @SerialName("recipe_id")
    val recipeId: Int? = null
)