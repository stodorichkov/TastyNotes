package com.example.tastynotes.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.model.Recipe
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.SupabaseService
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RecipesViewModel(
    val navController: NavController,
    val isMine: Boolean = false
): ViewModel() {
    var recipes by mutableStateOf<List<Recipe>>(emptyList())

    init {
        viewModelScope.launch {
            recipes = if (isMine) {
                SupabaseService.getMineRecipes()
            } else {
                SupabaseService.getRecipes()
            }
        }
    }

    fun showDetails(recipe: Recipe) {
        navController.navigate(Screen.Recipe.route + "/${Json.encodeToString(recipe)}")
    }
}