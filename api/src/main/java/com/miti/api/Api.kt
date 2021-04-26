package com.miti.api

import com.miti.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    companion object {
        const val PATH = "http://194.58.111.240:8080"
    }

    @Headers(
            "Accept: application/json",
            "Content-Type: application/json",
            "Content-Length: 100"
    )
    @POST("$PATH/api/v1/authorization")
    fun authorize(@Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("$PATH/api/v1/category/getAllCategories")
    fun getAllCategories(): Call<List<Category>>

    @POST("$PATH/server/api/v1/user")
    fun addUser(@Body user: User): Call<User>

    @GET("$PATH/api/v1/recipe/getRecipesByCategoryId")
    fun getRecipesByCategoryId(
            @Query("categoryId") categoryId: String
    ): Call<List<Recipe>>

    @GET("$PATH/api/v1/recipe/getRecipeById")
    fun getRecipeById(
            @Query("id") recipeId: Long
    ): Call<Recipe>

}