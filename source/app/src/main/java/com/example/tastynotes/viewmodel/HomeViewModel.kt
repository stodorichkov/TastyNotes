package com.example.tastynotes.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TurnedIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.model.NavItem
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.LoadingManager
import com.example.tastynotes.service.SupabaseService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HomeViewModel(
    private val navController: NavController
): ViewModel() {
    val navItems = listOf(
        NavItem("Recipes", Icons.Default.FoodBank),
        NavItem("Saved", Icons.Default.TurnedIn),
        NavItem("Add", Icons.Default.Add),
        NavItem("Mine", Icons.Default.Save),
        NavItem("Settings", Icons.Default.Settings)
    )

    var selectedItem by mutableIntStateOf(0)

    fun addRecipe() {
        LoadingManager.show()
        viewModelScope.launch {
            val products = SupabaseService.getProducts()

            LoadingManager.dismiss()
            navController.navigate(Screen.RecipeForm.route + "/${Json.encodeToString(products)}")
        }
    }
}