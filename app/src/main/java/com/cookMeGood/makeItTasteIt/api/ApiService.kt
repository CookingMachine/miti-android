  package com.cookMeGood.makeItTasteIt.api

import android.content.Context
import com.cookMeGood.makeItTasteIt.utils.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiService {

    private var client = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    private const val PREF_NAME = "access_preferences"
    private val sharedPreferences = ApplicationContext.getContext()
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getApi(): Api {

        val jwtToken = sharedPreferences.getString("access_token", "") ?: ""

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS

        client.addNetworkInterceptor(logging)
        client.addInterceptor { chain ->

            val request = chain.request().newBuilder()

            if (jwtToken.isNotEmpty()) {
                request.header("Authorization", "Bearer $jwtToken")
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