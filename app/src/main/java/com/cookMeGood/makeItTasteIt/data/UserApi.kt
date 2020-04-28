package com.cookMeGood.makeItTasteIt.data

import com.cookMeGood.makeItTasteIt.data.dto.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("$API_PATH/users/Auth")
    fun checkUser(@Query("login") login: String?,
                  @Query("pass") pass: String?): Call<User?>?

    companion object {
        const val API_PATH = "http://10.0.2.2:8080"
    }
}