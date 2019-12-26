package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    String API_PATH = "http://localhost:8080";

    @GET(API_PATH + "/categories")
    Call<List<Category>> getAllCategories();

    @GET(API_PATH + "/categories/{id}")
    Call<Category> getCategoryByID();
}
