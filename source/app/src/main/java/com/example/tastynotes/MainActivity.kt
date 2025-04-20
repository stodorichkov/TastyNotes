package com.example.tastynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tastynotes.view.screens.AppNavigation
import com.example.tastynotes.ui.theme.TastyNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TastyNotesTheme {
                AppNavigation()
            }
        }
    }
}