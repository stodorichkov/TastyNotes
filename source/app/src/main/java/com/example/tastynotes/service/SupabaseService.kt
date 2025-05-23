package com.example.tastynotes.service

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.tastynotes.constant.Constants
import com.example.tastynotes.model.Product
import com.example.tastynotes.model.Recipe
import com.example.tastynotes.model.Step
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
    private val recipes = "recipes"
    private val products = "products"
    private val steps = "steps"

    suspend fun registerUser(username: String, email: String, password: String): Result<String?> {
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
                user = supabase.from(users).insert(user) {
                    select()
                }.decodeSingle()


                logInfo(Constants.SIGN_UP, Constants.SUCCESS)
                Result.success(user.id)
            }
        } catch (e: AuthRestException) {
            logError(Constants.SIGN_UP, e.errorDescription)
            Result.failure(Exception(Constants.SIGN_UP_FAILED))
        } catch (e: PostgrestRestException) {
            logError(Constants.SIGN_UP, e.localizedMessage)
            Result.failure(Exception(Constants.SIGN_UP_FAILED))
        }
    }

    suspend fun loginUser(username: String, password: String): Result<String?> {
        return try {
            val user = supabase.from(users).select(columns = Columns.list()) {
                filter {
                    User::username eq username
                }
            }.decodeSingleOrNull<User>()

            if (user != null && verifyPassword(user.password.toString(), password)) {
                supabase.auth.signInWith(Email) {
                    this.email = user.email.toString()
                    this.password = user.password.toString()
                }

                logInfo(Constants.SIGN_IN, Constants.SUCCESS)
                Result.success(user.id)
            } else if (user == null) {
                logError(Constants.SIGN_IN, Constants.SIGN_IN_FAILED_USER)
                Result.failure(Exception(Constants.SIGN_IN_FAILED))
            } else {
                logError(Constants.SIGN_IN, Constants.SIGN_IN_FAILED_PASSWORD)
                Result.failure(Exception(Constants.SIGN_IN_FAILED))
            }
        } catch (e: AuthRestException) {
            logError(Constants.SIGN_IN, e.errorDescription)
            Result.failure(Exception(Constants.SIGN_IN_FAILED))
        } catch (e: PostgrestRestException) {
            logError(Constants.SIGN_IN, e.localizedMessage)
            Result.failure(Exception(Constants.SIGN_IN_FAILED))
        }
    }

    suspend fun logout(): Result<String?> {
        return try {
            supabase.auth.signOut()
            logInfo(Constants.SIGN_OUT, Constants.SUCCESS)
            Result.success(null)
        } catch (e: AuthRestException) {
            logError(Constants.SIGN_OUT , e.errorDescription)
            Result.failure(Exception(Constants.SIGN_IN_FAILED))
        }
    }

    suspend fun addRecipe(recipe: Recipe): Result<Recipe> {
        return try {
            val recipe = supabase.from(recipes).insert(recipe) {
                select()
            }.decodeSingle<Recipe>()

            logInfo(Constants.RECIPE_ADD, Constants.SUCCESS)
            Result.success(recipe)
        } catch (e: PostgrestRestException) {
            logError(Constants.RECIPE_ADD, e.localizedMessage)
            Result.failure(Exception(Constants.RECIPE_ADD_FAILED))
        }
    }


    suspend fun addProducts(products: List<Product>): Result<String?> {
        return try {
            supabase.from(this.products).insert(products)

            logInfo(Constants.RECIPE_ADD, Constants.SUCCESS)
            Result.success(null)
        } catch (e: PostgrestRestException) {
            logError(Constants.RECIPE_ADD, e.localizedMessage)
            Result.failure(Exception(Constants.RECIPE_ADD_FAILED))
        }
    }

    suspend fun addSteps(steps: List<Step>): Result<String?> {
        return try {
            supabase.from(this.steps).insert(steps)

            supabase.

            logInfo(Constants.RECIPE_ADD, Constants.SUCCESS)
            Result.success(null)
        } catch (e: PostgrestRestException) {
            logError(Constants.RECIPE_ADD, e.localizedMessage)
            Result.failure(Exception(Constants.RECIPE_ADD_FAILED))
        }
    }

    suspend fun getRecipes(onlyMine: Boolean = false): List<Recipe> {
        return supabase.from(recipes).select(Columns.raw(
            "id, name, timestamp, users(id, username)"
        )){
            if (onlyMine) {
                filter {
                    eq("author_id", DeviceDataService.getUserId().toString())
                }
            }
        }.decodeList()
    }

    suspend fun getRecipeByID(id: Int?): Result<Recipe> {
        return if (id != null) {
            val result: Recipe = supabase.from(recipes).select(
                Columns.raw(
                    "id, name, timestamp, users(id, username), " +
                    "products(id, name, quantity), steps(id, text)"
                )
            ) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle()

            logInfo(Constants.RECIPE_GET, Constants.SUCCESS)
            Result.success(result)
        } else {
            logError(Constants.RECIPE_GET, Constants.RECIPE_GET_FAILED)
            Result.failure(Exception(Constants.RECIPE_GET_FAILED))
        }
    }


    private fun logError(reason: String, message: String?) {
        Log.e(Constants.SUPABASE, "${reason}: ${message}")
    }

    private fun logInfo(reason: String, message: String?) {
        Log.i(Constants.SUPABASE, "${reason}: ${message}")
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(storedHash: String, password: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), storedHash.toCharArray()).verified
    }
}