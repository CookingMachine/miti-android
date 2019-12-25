package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecipeApi {
    @POST("/recipes")
    public Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("/recipes/{id}")
    public Call<Recipe> getRecipeById(@Path("id") Long id);

    @GET("/recipes")
    public Call<List<Recipe>> getAllRecipes();
}
