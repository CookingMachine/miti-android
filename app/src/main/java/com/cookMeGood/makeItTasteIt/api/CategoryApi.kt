package com.cookMeGood.makeItTasteIt.api

import com.cookMeGood.makeItTasteIt.dto.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    companion object {
        private const val API_PATH = "https://miti-serv.herokuapp.com"
    }

    @GET("$API_PATH/getAllCategories")
    fun getAllCategories(): Call<List<Category>>
}