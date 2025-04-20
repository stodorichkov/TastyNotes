package com.example.tastynotes.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val password: String
)
