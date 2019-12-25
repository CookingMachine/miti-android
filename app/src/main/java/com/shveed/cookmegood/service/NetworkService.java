package com.shveed.cookmegood.service;

import com.shveed.cookmegood.data.RecipeApi;
import com.shveed.cookmegood.data.UserApi;
import com.shveed.cookmegood.entity.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "localhost:8080";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public UserApi getUserApi() {
        return mRetrofit.create(UserApi.class);
    }

    public RecipeApi getRecipeApi(){
        return mRetrofit.create(RecipeApi.class);
    }


}
