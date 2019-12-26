package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecipeApi {

    String API_PATH = "http://10.0.2.2:8080";

    @POST(API_PATH)
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET(API_PATH + "/{id}")
    Call<Recipe> getRecipeById(@Path("id") Long id);

    @GET(API_PATH)
    Call<List<Recipe>> getAllRecipes();
}
