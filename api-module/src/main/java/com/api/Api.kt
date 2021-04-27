package com.api

import com.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    companion object {
        const val BASE_URL = "http://194.58.111.240:8080"
        const val PATH = "/server/api/v1"
    }

    @Headers("Content-Type: application/json")
    @POST("$PATH/authorization")
    fun authorize(@Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("$PATH/category")
    fun getAllCategories(): Call<List<Category>>

    @POST("$PATH/user")
    fun addUser(@Body user: User): Call<User>

    @GET("$PATH/recipe/getByCategoryId")
    fun getRecipesByCategoryId(
            @Query("categoryId") categoryId: String
    ): Call<List<Recipe>>

    @GET("$PATH/recipe")
    fun getRecipeById(
            @Query("id") recipeId: Long
    ): Call<Recipe>

}