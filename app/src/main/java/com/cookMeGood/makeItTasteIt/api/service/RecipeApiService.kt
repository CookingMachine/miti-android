package com.cookMeGood.makeItTasteIt.api.service

import com.cookMeGood.makeItTasteIt.api.RecipeApi
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RecipeApiService {

    private var retrofit: Retrofit? = null
    private const val API_PATH = "https://miti-serv.herokuapp.com/"

    init {
        retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(API_PATH)
                .build()
    }

    fun getApi(): RecipeApi {
        return retrofit!!.create(RecipeApi::class.java)
    }
}