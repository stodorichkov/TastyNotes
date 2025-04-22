package com.example.tastynotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tastynotes.constant.Constants
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.AlertManager
import com.example.tastynotes.service.DeviceDataService
import com.example.tastynotes.service.LoadingManager
import com.example.tastynotes.service.SupabaseService
import kotlinx.coroutines.launch

class SettingsViewModel(private val navController: NavController,): ViewModel() {
    val username = DeviceDataService.getUsername()

    fun logout() {
        LoadingManager.show()
        viewModelScope.launch {
            val result = SupabaseService.logout()
            LoadingManager.dismiss()
            if (result.isFailure) {
                AlertManager.show(
                    title = Constants.WARNING,
                    message = result.exceptionOrNull()?.message.toString()
                )
            } else {
                DeviceDataService.resetUserData()
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
}