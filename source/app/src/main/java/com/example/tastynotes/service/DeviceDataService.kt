package com.example.tastynotes.service

import android.content.Context
import android.content.SharedPreferences


object DeviceDataService {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
    }

    fun saveUserData(username: String, id: String) {
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("id", id)
            apply()
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("id", null)
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun resetUserData() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}