package com.example.tastynotes.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AlertManager {
    var isShown by mutableStateOf(false)
    var title by  mutableStateOf("")
    var message by mutableStateOf("")

    fun show(title: String, message: String) {
        this.title = title
        this.message = message
        this.isShown = true
    }

    fun dismiss() {
        this.title = ""
        this.message = ""
        this.isShown = false
    }
}