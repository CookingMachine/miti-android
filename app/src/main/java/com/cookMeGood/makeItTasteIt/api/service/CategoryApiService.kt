package com.cookMeGood.makeItTasteIt.api.service

import com.cookMeGood.makeItTasteIt.api.CategoryApi
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object CategoryApiService {

    private var retrofit: Retrofit? = null
    private const val API_PATH = "https://miti-serv.herokuapp.com/"

    init {
        retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(API_PATH)
                .build()
    }

    fun getApi(): CategoryApi {
        return retrofit!!.create(CategoryApi::class.java)
    }
}