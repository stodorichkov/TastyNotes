package com.example.tastynotes.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.TurnedIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.constant.Constants
import com.example.tastynotes.model.NavItem
import com.example.tastynotes.model.Product
import com.example.tastynotes.model.Recipe
import com.example.tastynotes.model.Step
import com.example.tastynotes.service.AlertManager
import com.example.tastynotes.service.LoadingManager
import com.example.tastynotes.service.SupabaseService
import kotlinx.coroutines.launch

class RecipeFormViewModel(
    val navController: NavController
): ViewModel() {
    val navItems = listOf(
        NavItem("Details", Icons.Default.FoodBank),
        NavItem("Ingredients", Icons.Default.TurnedIn),
        NavItem("Steps", Icons.Default.Add),
    )
    var selectedItem by mutableIntStateOf(0)


    var name by mutableStateOf("")
    val steps = mutableStateListOf<Step>()
    var products = mutableStateListOf<Product>()

    fun addProduct() {
        val validationMessage = validateForm()
        if (validationMessage != null) {
            AlertManager.show(
                title = Constants.WARNING,
                message = validationMessage
            )
            return
        }

        LoadingManager.show()
        viewModelScope.launch {
            val recipeResult = SupabaseService.addRecipe(Recipe(name = name))

            if (recipeResult.isFailure) {
                LoadingManager.dismiss()
                AlertManager.show(
                    title = Constants.WARNING,
                    message = recipeResult.exceptionOrNull()?.message.toString()
                )
            } else {
                products.forEach { it.recipeId = recipeResult.getOrNull()?.id }
                val productsResult = SupabaseService.addProducts(products)

                if (productsResult.isFailure) {
                    LoadingManager.dismiss()
                    AlertManager.show(
                        title = Constants.WARNING,
                        message = recipeResult.exceptionOrNull()?.message.toString()
                    )
                } else {
                    steps.forEach { it.recipeId = recipeResult.getOrNull()?.id }
                    val stepsResult = SupabaseService.addSteps(steps)

                    LoadingManager.dismiss()
                    if(stepsResult.isFailure) {
                        AlertManager.show(
                            title = Constants.WARNING,
                            message = recipeResult.exceptionOrNull()?.message.toString()
                        )
                    } else {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    private fun validateForm(): String? {
        return if (
            name.isEmpty() ||
            products.any { it.name.isEmpty() || it.quantity.isEmpty() }  ||
            steps.any { it.text.isEmpty() }
        ) {
            Constants.FORM_EMPTY
        } else {
            null
        }
    }
}