package com.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    const val PREF_NAME = "access_preferences"
    const val ACCESS_TOKEN_KEY = "access_token"
    private const val HEADER_AUTHORIZATION = "Authorization"

    private var client = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null
    var jwtToken: String? = null

    fun getApi(): Api {

        val logging = HttpLoggingInterceptor()
        val gson = GsonBuilder().setLenient().create()

        logging.level = HttpLoggingInterceptor.Level.HEADERS

        client.addNetworkInterceptor(logging)
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()

            if (jwtToken != null) {
                request.header(HEADER_AUTHORIZATION, "Bearer $jwtToken")
            }
            chain.proceed(request.build())
        }

        retrofit = Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Api.BASE_URL)
                .build()

        return retrofit!!.create(Api::class.java)
    }
}