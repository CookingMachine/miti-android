package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryApi {

    @GET("/categories")
    public Call<List<Category>> getAllCategories();

    @GET("/categories/{id}")
    public Call<Category> getCategoryByID();
}
