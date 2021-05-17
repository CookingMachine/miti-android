package com.api

import com.api.dto.*
import com.api.dto.request.SearchRecipeRequest
import retrofit2.Call
import retrofit2.http.*

interface Api {

    companion object {
        const val BASE_URL = "http://194.58.111.240:8080"
        const val PATH = "/server/api/v1"
    }

    @Headers("Content-Type: application/json")
    @POST("$PATH/authorization")
    fun authorize(@Body request: LoginRequest): Call<LoginResponse>

    @GET("$PATH/category")
    fun getAllCategories(): Call<List<Category>>

    @POST("$PATH/user")
    fun addUser(@Body user: User): Call<User>

    @GET("$PATH/recipe/getByCategoryId/{id}")
    fun getRecipesByCategoryId(@Path("id") categoryId: String): Call<List<Recipe>>

    @GET("$PATH/recipe/{id}")
    fun getRecipeById(@Path("id") recipeId: Long): Call<Recipe>

    @POST("$PATH/recipe/")
    fun addRecipe(@Body recipe: RecipeAdditionRequest): Call<Recipe>

    @GET("$PATH/searchRecipe")
    fun getRecipesByCriteria(
            @Body recipeRequest: SearchRecipeRequest,
            @Path("sort") sort: String
    ): Call<List<Recipe>>
}
