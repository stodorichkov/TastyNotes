package com.example.tastynotes.model

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Registration : Screen("registration")
    object Login : Screen("login")
    object Home: Screen("home")
    object RecipeForm: Screen("recipeForm")
    object Recipe: Screen("recipe")
}