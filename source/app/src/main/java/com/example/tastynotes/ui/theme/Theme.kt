package com.example.tastynotes.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = PrimaryText
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = PrimaryText
)

@Composable
fun TastyNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    // status bar color
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.background.toArgb()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}