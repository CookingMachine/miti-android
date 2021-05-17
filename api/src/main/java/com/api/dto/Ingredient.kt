package com.api.dto

import com.google.gson.annotations.SerializedName

data class Ingredient(

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("amount")
        var amount: String? = null,

)