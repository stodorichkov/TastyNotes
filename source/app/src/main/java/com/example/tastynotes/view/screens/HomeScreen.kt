package com.example.tastynotes.view.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.tastynotes.service.DeviceDataService

@Composable
fun HomeScreen(navController: NavController) {
    DeviceDataService.resetUserData(LocalContext.current)
    navController.navigate(Screen.Welcome.route) {
        popUpTo(0) { inclusive = true }
    }
}