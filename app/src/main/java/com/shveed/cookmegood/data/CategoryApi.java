package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    String API_PATH = "http://10.0.2.2:8080";

    @GET(API_PATH + "/categories/all")
    Call<List<Category>> getAllCategories();
}
