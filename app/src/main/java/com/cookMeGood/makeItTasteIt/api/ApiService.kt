package com.cookMeGood.makeItTasteIt.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiService {

    private var client = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    const val prefName = "access_preferences"
//    private val sharedPreferences = ApplicationContext.getContext()
//            .getSharedPreferences(prefName, Context.MODE_PRIVATE)

    private lateinit var jwtToken: String

    fun getApi(): Api {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

//        jwtToken = sharedPreferences.getString("access_token", "") ?: ""

        client.addNetworkInterceptor(logging)

        client.addInterceptor { chain ->

            val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNTIwODgwMSwiaWF0IjoxNjA1MTkwODAxfQ.GBNXHNz09o5afUx6XcXtBa-F3eYrN6PXMd2mIwz-ov2QYolqnPxeCL60DIZ57h7p4U8O7KOM8qdLTvN6hD-MBg")

            chain.proceed(request.build())
        }

        retrofit = Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(Api.API_PATH)
                .build()

        return retrofit!!.create(Api::class.java)
    }
}