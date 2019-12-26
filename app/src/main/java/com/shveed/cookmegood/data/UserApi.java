package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    String API_PATH = "http://10.0.2.2:8080";

//    @GET(API_PATH + "/users")
//    Call<User> getUserByLogin(@Query("login") Long login);
//
//    @GET(API_PATH + "/users/all")
//    Call<List<User>> getAllUsers();
//
//    @POST(API_PATH + "/users")
//    Call<User> addUser(@Body User user);

    @GET(API_PATH + "/users/Auth")
    Call<User> checkUser(@Query("login") String login,
                         @Query("pass") String pass);


}
