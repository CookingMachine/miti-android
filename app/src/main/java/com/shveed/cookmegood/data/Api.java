package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("/users/{id}")
    public Call<User> getPostWithID(@Path("id") int id);
}
