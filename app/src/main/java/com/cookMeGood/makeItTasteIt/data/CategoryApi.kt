package com.cookMeGood.makeItTasteIt.data

import com.cookMeGood.makeItTasteIt.data.dto.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {
    @get:GET("$API_PATH/categories/all")
    val allCategories: Call<List<Category?>?>?

    companion object {
       const val API_PATH = "http://10.0.2.2:8080"
    }
}