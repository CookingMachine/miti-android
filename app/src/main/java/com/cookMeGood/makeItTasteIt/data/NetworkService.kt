package com.cookMeGood.makeItTasteIt.data

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class NetworkService() {
    private val mRetrofit: Retrofit
        private var mInstance: NetworkService? = null
        private  val BASE_URL = "http://localhost:8080"

    init {
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
    }

    val instance: NetworkService?
        get() {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }

    val userApi: UserApi
        get() = mRetrofit.create(UserApi::class.java)

    val recipeApi: RecipeApi
        get() = mRetrofit.create(RecipeApi::class.java)

    val categoryApi: CategoryApi
        get() = mRetrofit.create(CategoryApi::class.java)
}