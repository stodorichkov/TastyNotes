package com.example.tastynotes.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tastynotes.model.Screen
import com.example.tastynotes.service.DeviceDataService

class AppRootViewModel: ViewModel() {
    val isLogged = DeviceDataService.getUserId() != null
    val startDestination = if (isLogged) Screen.Home.route else Screen.Welcome.route
}