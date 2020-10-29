package com.cookMeGood.makeItTasteIt.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiService {

    private var client = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null
    private const val API_PATH = "https://miti-serv.herokuapp.com/"


    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", RuntimeStorage.accessToken!!)
                            .build()

                    chain.proceed(request)
                    }
                .addNetworkInterceptor(logging)


        retrofit = Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(API_PATH)
                .build()
    }

    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }
}