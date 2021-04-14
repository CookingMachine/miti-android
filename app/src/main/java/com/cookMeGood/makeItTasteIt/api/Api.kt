package com.cookMeGood.makeItTasteIt.api

import com.cookMeGood.makeItTasteIt.api.model.LoginRequest
import com.cookMeGood.makeItTasteIt.api.model.LoginResponse
import com.cookMeGood.makeItTasteIt.api.dto.Category
import com.cookMeGood.makeItTasteIt.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.api.dto.User
import retrofit2.Call
import retrofit2.http.*

interface Api {

    companion object {
        const val PATH = "https://miti-serv.herokuapp.com"
    }

    @Headers(
            "Accept: application/json",
            "Content-Type: application/json",
            "Content-Length: 100"
    )
    @POST("$PATH/auth")
    fun authorize(@Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("${PATH}/api/v1/category/getAllCategories")
    fun getAllCategories(): Call<List<Category>>

    @POST("${PATH}/addUser")
    fun addUser(@Body user: User): Call<User>

    @GET("${PATH}/getRecipesByCategoryId")
    fun getRecipesByCategoryId(
            @Query("categoryId") categoryId: String
    ): Call<List<Recipe>>

    @GET("${PATH}/getRecipeById")
    fun getRecipeById(
            @Query("id") recipeId: Long
    ): Call<Recipe>

}