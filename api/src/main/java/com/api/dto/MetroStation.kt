package com.api.dto

import com.google.gson.annotations.SerializedName

data class MetroStation(

        @SerializedName("id")
        var id: Int? = null,

        @SerializedName("title")
        var title: String? = null
)