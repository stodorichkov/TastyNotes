package com.example.tastynotes.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null
)
