package com.api.dto.request

import com.api.dto.Ingredient
import com.google.gson.annotations.SerializedName

data class SearchRecipeRequest (

        @SerializedName("category")
        var category: String? = null,

        @SerializedName("kitchen")
        var kitchen: String? = null,

        @SerializedName("caloriesMin")
        var caloriesMin: Int? = 0,

        @SerializedName("caloriesMax")
        var caloriesMax: Int? = null,

        @SerializedName("timeMin")
        var timeMin: Int? = 0,

        @SerializedName("timeMax")
        var timeMax: Int? = null,

        @SerializedName("substring")
        var substring: String? = null,

        @SerializedName("ingredients")
        var ingredients: List<Ingredient>? = null

)
