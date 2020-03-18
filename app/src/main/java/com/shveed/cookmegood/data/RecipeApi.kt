package com.shveed.cookmegood.data

import com.shveed.cookmegood.data.dto.Recipe
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeApi {
    @POST(API_PATH)
    fun createRecipe(@Body recipe: Recipe?): Call<Recipe?>?

    @GET("$API_PATH/{id}")
    fun getRecipeById(@Path("id") id: Long?): Call<Recipe?>?

    @get:GET(API_PATH)
    val allRecipes: Call<List<Recipe?>?>?

    companion object {
        const val API_PATH = "http://10.0.2.2:8080"
    }
}