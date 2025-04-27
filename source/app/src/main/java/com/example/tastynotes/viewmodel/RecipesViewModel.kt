package com.example.tastynotes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.model.Recipe
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.LoadingManager
import com.example.tastynotes.service.SupabaseService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class RecipesViewModel(
    val navController: NavController,
    val isMine: Boolean = false
): ViewModel() {
    var recipes by mutableStateOf<List<Recipe>>(emptyList())

    init {
        viewModelScope.launch {
            recipes = SupabaseService.getRecipes(onlyMine = isMine)
        }
    }

    fun showDetails(recipeID: Int?) {
        LoadingManager.show()
        viewModelScope.launch {
            val result = SupabaseService.getRecipeByID(recipeID)

            LoadingManager.dismiss()
            if(result.isFailure) {

            } else {
                navController.navigate(Screen.Recipe.route + "/${Json.encodeToString(result.getOrNull())}")
            }
        }
    }
}