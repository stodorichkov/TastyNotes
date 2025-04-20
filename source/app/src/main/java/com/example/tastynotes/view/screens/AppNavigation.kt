package com.example.tastynotes.view.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tastynotes.service.DeviceDataService
import com.example.tastynotes.view.custom.Alert
import com.example.tastynotes.view.custom.Loading

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Registration : Screen("registration")
    object Login : Screen("login")
    object Home: Screen("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isLogged = DeviceDataService.getUserId(context) != null
    val startDestination = if (isLogged) Screen.Home.route else Screen.Welcome.route

    Alert()
    Loading()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Registration.route) {
            UserFormScreen(navController, false)
        }
        composable(Screen.Login.route) {
            UserFormScreen(navController, true)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}

