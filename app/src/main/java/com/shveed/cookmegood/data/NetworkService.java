package com.shveed.cookmegood.data;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;

    private static final String BASE_URL = "http://localhost:8080/";

    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
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
