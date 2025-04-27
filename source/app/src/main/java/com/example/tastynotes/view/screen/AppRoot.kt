package com.example.tastynotes.view.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tastynotes.model.Product
import com.example.tastynotes.model.Recipe
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.DeviceDataService
import com.example.tastynotes.view.custom.Alert
import com.example.tastynotes.view.custom.Loading
import com.example.tastynotes.viewmodel.AppRootViewModel
import com.example.tastynotes.viewmodel.RecipeFormViewModel
import com.example.tastynotes.viewmodel.RecipeViewModel
import kotlinx.serialization.json.Json

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppRoot() {
    DeviceDataService.init(LocalContext.current)
    val navController = rememberNavController()
    val viewModel = AppRootViewModel()

    Alert()
    Loading()
    NavHost(navController = navController, startDestination = viewModel.startDestination) {
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
        composable(Screen.RecipeForm.route+"/{products}") { backStackEntry ->
            val str = backStackEntry.arguments?.getString("products")
            val products = str?.let {
                Json.decodeFromString<List<Product>>(it)
            }
            if (products != null) {
                RecipeForm(RecipeFormViewModel(navController, products))
            }
        }
        composable(Screen.Recipe.route+"/{recipe}") { backStackEntry ->
            val str = backStackEntry.arguments?.getString("recipe")
            val recipe = str.let { Json.decodeFromString<Recipe>(it.toString())}
            RecipeScreen(RecipeViewModel(navController, recipe))
        }
    }
}

