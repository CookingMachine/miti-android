package com.cookMeGood.makeItTasteIt.api

import com.cookMeGood.makeItTasteIt.dto.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    companion object {
        private const val API_PATH = "https://miti-serv.herokuapp.com"
    }

    @GET("$API_PATH/getRecipesByCategoryId")
    fun getRecipesByCategoryId(
            @Query("categoryId") categoryId: String
    ): Call<List<Recipe>>

    @GET("$API_PATH/getRecipeById")
    fun getRecipeById(
            @Query("id") recipeId: Long
    ): Call<Recipe>
}