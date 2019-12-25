package com.shveed.cookmegood.data;

import com.shveed.cookmegood.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    String API_PATH = "http://localhost:8080/users";

    @GET(API_PATH + "")
    Call<User> getUserByLogin(@Query("login") Long login);

    @GET(API_PATH + "/all")
    Call<List<User>> getAllUsers();

    @POST(API_PATH + "")
    Call<User> addUser(@Body User user);

    @GET(API_PATH + "/auth")
    Call<User> checkUser(@Query("login") String login,
                         @Query("pass") String pass);


}
