package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CalorieContent(
        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("calories")
        var calories: Long? = null,

        @SerializedName("protein")
        var protein: Long? = null,

        @SerializedName("fat")
        var fat: Long? = null,

        @SerializedName("carbohydrates")
        var carbohydrates: Long? = null
): Serializable
