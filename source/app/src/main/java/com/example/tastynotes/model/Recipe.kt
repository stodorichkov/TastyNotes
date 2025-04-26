package com.example.tastynotes.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable
data class Recipe(
    val id: Int? = null,
    val name: String,
    @SerialName("users")
    val author: User? = null,
    val timestamp: Instant? = null,
    val steps: List<Step> = emptyList(),
    val ingredients: List<Ingredient> = emptyList()
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(): String {
        val javaInstant = java.time.Instant.ofEpochMilli(timestamp?.toEpochMilliseconds() ?: 0)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:MM")
            .withZone(ZoneId.systemDefault())

        return formatter.format(javaInstant)
    }
}