package com.example.tastynotes.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LoadingManager {
    var isShown by mutableStateOf(false)

    fun show() {
        isShown = true
    }

    fun dismiss() {
        isShown = false
    }
}