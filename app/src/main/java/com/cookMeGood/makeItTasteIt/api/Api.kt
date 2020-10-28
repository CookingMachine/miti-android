package com.cookMeGood.makeItTasteIt.api

import com.cookMeGood.makeItTasteIt.api.model.LoginRequest
import com.cookMeGood.makeItTasteIt.api.model.LoginResponse
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.dto.Recipe
import com.cookMeGood.makeItTasteIt.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    companion object {
        private const val API_PATH = "https://miti-serv.herokuapp.com"
    }

    // AUTHENTICATION

    @POST("$API_PATH/auth")
    fun authorize(@Body request: LoginRequest): Call<LoginResponse>

    // CATEGORIES

    @GET("${API_PATH}/getAllCategories")
    fun getAllCategories(): Call<List<Category>>

    //USER

    @POST("${API_PATH}/addUser")
    fun addUser(@Body user: User)

    //RECIPE

    @GET("${API_PATH}/getRecipesByCategoryId")
    fun getRecipesByCategoryId(
            @Query("categoryId") categoryId: String
    ): Call<List<Recipe>>

    @GET("${API_PATH}/getRecipeById")
    fun getRecipeById(
            @Query("id") recipeId: Long
    ): Call<Recipe>

}