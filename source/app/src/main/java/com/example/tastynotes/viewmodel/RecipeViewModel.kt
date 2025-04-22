package com.example.tastynotes.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.TurnedIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tastynotes.model.NavItem
import com.example.tastynotes.model.Recipe

class RecipeViewModel(
    val navController: NavController,
    val recipe: Recipe
): ViewModel() {
    val navItems = listOf(
        NavItem("Details", Icons.Default.FoodBank),
        NavItem("Ingredients", Icons.Default.TurnedIn),
        NavItem("Steps", Icons.Default.Add),
    )
    var selectedItem by mutableIntStateOf(0)
}