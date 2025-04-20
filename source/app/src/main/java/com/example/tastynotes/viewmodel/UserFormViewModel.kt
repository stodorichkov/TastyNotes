package com.example.tastynotes.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.service.AlertManager
import com.example.tastynotes.constant.Constants
import com.example.tastynotes.service.DeviceDataService
import com.example.tastynotes.service.LoadingManager
import com.example.tastynotes.service.SupabaseService
import com.example.tastynotes.view.screens.Screen
import kotlinx.coroutines.launch

class UserFormViewModel(
    private val context: Context,
    private val navController: NavController,
    private val isLogin: Boolean = false
): ViewModel() {
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    fun onSubmit() {
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
            val result = if (isLogin) {
                SupabaseService.loginUser(username, password)
            } else {
                SupabaseService.registerUser(username, email, password)
            }

            LoadingManager.dismiss()
            if (result.isFailure) {
                AlertManager.show(
                    title = Constants.WARNING,
                    message = result.exceptionOrNull()?.message.toString()
                )
            } else {
                DeviceDataService.saveUserData(context, username, result.getOrNull().toString())
                navController.navigate(Screen.Home.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    private fun validateForm(): String? {
        return if (username.isEmpty() || password.isEmpty() || (!isLogin && (email.isEmpty() || confirmPassword.isEmpty()))) {
            Constants.FORM_EMPTY
        } else if (username.length < 3) {
            Constants.USENAME_INVALID
        } else if (!isLogin && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Constants.EMAIL_INVALID
        }
        else if (!isLogin && !password.matches(Regex(Constants.PASSWORD_REGEX))) {
            Constants.PASSWORD_INVALID
        } else if (!isLogin && !password.equals(confirmPassword)) {
            Constants.PASSWORD_NOT_COFIRMED
        }
        else {
            null
        }
    }


}