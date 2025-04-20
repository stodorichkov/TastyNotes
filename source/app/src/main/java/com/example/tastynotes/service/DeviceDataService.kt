package com.example.tastynotes.service

import android.content.Context

object DeviceDataService {
    fun saveUserData(context: Context, username: String, id: String) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("id", id)
            apply()
        }
    }

    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("id", null)
    }
    fun getUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    fun resetUserData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}