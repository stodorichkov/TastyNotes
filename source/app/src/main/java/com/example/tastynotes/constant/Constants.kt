package com.example.tastynotes.constant


object Constants {
    val WARNING = "Warning"
    val SUCCESS = "Success"

    // Supabase
    val SUPABASE= "Supabase"
    val POSTGRE = "Postgre"
    val SIGN_OUT = "Sign Out"

    // Sign up messages
    val SIGN_UP = "Sign Up"
    val USERNAME_EXIST = "User with this username already exist"
    val EMAIL_EXIST = "User with this email already exist"
    val SIGN_UP_FAILED = "Sign up failed"

    // Sign in messages
    val SIGN_IN = "Sign In"
    val SIGN_IN_FAILED = "Wrong username or password"
    val SIGN_IN_FAILED_PASSWORD = "Password"
    val SIGN_IN_FAILED_USER = "User not found"

    // Recipe
    val RECIPE_ADD = "Recipe Add"
    val RECIPE_ADD_FAILED = "Something went wrong"
    val RECIPE_GET= "Recipe Get"
    val RECIPE_GET_FAILED = "Recipe with id not found"

    // Validation messages
    val FORM_EMPTY = "You must fill the form"
    val USENAME_INVALID = "Username Invalid. It must be at least 3 characters"
    val EMAIL_INVALID = "Email Invalid"
    val PASSWORD_INVALID = "Password Invalid. Password must contain uppercase, lowercase, number, symbol and be at least 8 characters"
    val PASSWORD_NOT_COFIRMED = "Password Invalid. Password not confirmed"

    // Regexes
    val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$"
}