package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/users/{id}")
    public Call<User> getUserByID(@Path("id") Long id);

    @GET("/users")
    public Call<List<User>> getAllUsers();

    @POST("/users")
    public Call<User> addUser(@Body User user);


}
