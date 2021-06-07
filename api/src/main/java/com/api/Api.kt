package com.api

import com.api.dto.Category
import com.api.dto.MainContent
import com.api.dto.Recipe
import com.api.dto.request.LoginRequest
import com.api.dto.request.RecipeAdditionRequest
import com.api.dto.request.SearchRecipeRequest
import com.api.dto.request.UserRegistrationRequest
import com.api.dto.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object {
        const val BASE_URL = "http://194.58.111.240:8080"
        const val PATH = "/test_server2/api/v1"
    }

    @POST("$PATH/authorization")
    fun authorize(@Body request: LoginRequest): Call<UserResponse>

    @GET("$PATH/authorization/validate")
    fun validateToken(@Header("Authorization") jwtToken: String): Call<UserResponse>

    @GET("$PATH/category")
    fun getAllCategories(): Call<List<Category>>

    @POST("$PATH/user")
    fun addUser(@Body userRegistrationRequest: UserRegistrationRequest): Call<UserResponse>

    @POST("$PATH/user/addFavouriteRecipe")
    fun addFavouriteRecipe(
        @Query("userId") userId: Long,
        @Query("recipeId") recipeId: Long
    ): Call<ResponseBody>

    @DELETE("$PATH/user/deleteFavouriteRecipe")
    fun deleteFavouriteRecipe(
        @Query("userId") userId: Long,
        @Query("recipeId") recipeId: Long
    ): Call<ResponseBody>

    @GET("$PATH/recipe/getByCategoryId/{id}")
    fun getRecipesByCategoryId(@Path("id") categoryId: String): Call<List<Recipe>>

    @GET("$PATH/recipe/{id}")
    fun getRecipeById(@Path("id") recipeId: Long): Call<Recipe>

    @POST("$PATH/recipe/")
    fun addRecipe(@Body recipe: RecipeAdditionRequest): Call<Recipe>

    @GET("$PATH/recipe/getFavourites/{userId}")
    fun getFavouritesByUserId(@Path("userId") userId: Long): Call<List<Recipe>>

    @GET("$PATH/searchRecipe")
    fun getRecipesByCriteria(
        @Body recipeRequest: SearchRecipeRequest,
        @Path("sort") sort: String
    ): Call<List<Recipe>>

    @GET("$PATH/mainPage")
    fun getMainPageContent(): Call<MainContent>
}
