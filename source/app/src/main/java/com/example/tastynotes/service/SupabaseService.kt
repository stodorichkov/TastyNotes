package com.example.tastynotes.service

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.tastynotes.constant.Constants
import com.example.tastynotes.model.User
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns


object SupabaseService {
    private val supabaseUrl = "https://kqjnvebjhtwnvoarshch.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtxam52ZWJqaHR3bnZvYXJzaGNoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ4MTgwNjMsImV4cCI6MjA2MDM5NDA2M30.WG5SIURibabqb_YKkZpbMzQ8b1pGStX1vzsPEbvfYpA"
    private val supabase = createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Auth)
        install(Postgrest)
    }

    private val users = "users"

    suspend fun registerUser(username: String, email: String, password: String): Result<String> {
       return try {
           // Check user exist
            var user = supabase.from(users).select(columns = Columns.list()){
                filter {
                    or {
                        User::email eq email
                        User::username eq username
                    }
                }
            }.decodeSingleOrNull<User>()

            if (user != null) {
                var message = Constants.EMAIL_EXIST
                if (user.username.equals(username)) {
                    message = Constants.USERNAME_EXIST
                }

                logError(Constants.SIGN_UP, message)
                Result.failure(Exception(message))
            } else {
                // Crate user auth
                val hashPassword = hashPassword(password)
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = hashPassword
                }
                // Save user
                user = User(username = username, email = email, password = hashPassword)
                supabase.from(users).insert(user)

                Result.success(user.id)
            }
        } catch (e: AuthRestException) {
            logError(Constants.SIGN_UP, e.errorDescription)
            Result.failure(Exception(Constants.SIGN_UP_FAILED))
        } catch (e: PostgrestRestException) {
            logError(Constants.POSTGRE, e.localizedMessage)
            Result.failure(Exception(Constants.SIGN_UP_FAILED))
        }
    }

    suspend fun loginUser(username: String, password: String): Result<String> {
        return try {
            val user = supabase.from(users).select(columns = Columns.list()) {
                filter {
                    User::username eq username
                }
            }.decodeSingleOrNull<User>()

            if (user != null && verifyPassword(user.password, password)) {
                supabase.auth.signInWith(Email) {
                    this.email = user.email
                    this.password = user.password
                }

                Result.success(user.id)
            } else {
                Result.failure(Exception(Constants.SIGN_IN_FAILED))
            }
        } catch (e: AuthRestException) {
            logError(Constants.SIGN_IN, e.errorDescription)
            Result.failure(Exception(Constants.SIGN_IN_FAILED))
        } catch (e: PostgrestRestException) {
            logError(Constants.POSTGRE, e.localizedMessage)
            Result.failure(Exception(Constants.SIGN_IN_FAILED))
        }
    }

    private fun logError(reason: String, message: String?) {
        Log.e(Constants.SUPABASE, "${reason}: ${message}")
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(storedHash: String, password: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), storedHash.toCharArray()).verified
    }
}