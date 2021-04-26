package com.miti.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiService {

    const val PREF_NAME = "access_preferences"
    const val ACCESS_TOKEN_KEY = "access_token"
    private const val HEADER_AUTHORIZATION = "Authorization"

    private var client = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    fun getApi(context: Context): Api {

        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, "") ?: ""
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.HEADERS

        client.addNetworkInterceptor(logging)
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()

            if (jwtToken.isNotEmpty()) {
                request.header(HEADER_AUTHORIZATION, "Bearer $jwtToken")
            }
            chain.proceed(request.build())
        }

        retrofit = Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(Api.PATH)
                .build()

        return retrofit!!.create(Api::class.java)
    }
}