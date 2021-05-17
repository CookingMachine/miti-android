package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("value")
        var value: Int? = null,

        @SerializedName("recipe")
        var recipe: Recipe? = null,

        @SerializedName("user")
        var user: User? = null
): Serializable
