package com.cookMeGood.makeItTasteIt.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiService {

    private var client: OkHttpClient? = OkHttpClient()
    private var retrofit: Retrofit? = null
    private const val API_PATH = "https://miti-serv.herokuapp.com/"

    init {

        client!!.interceptors()
                .add(Interceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", RuntimeStorage.accessToken!!)
                            .method(original.method(), original.body())
                            .build()

                    chain.proceed(request)
                    }
                )


        retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(API_PATH)
                .build()
    }

    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }
}