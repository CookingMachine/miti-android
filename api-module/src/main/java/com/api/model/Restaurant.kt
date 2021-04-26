package com.api.model

import com.google.gson.annotations.SerializedName

data class Restaurant (

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("title")
        var title: String? = null,

        @SerializedName("kitchen")
        var kitchen: String? = null,

        @SerializedName("destination")
        var destination: String? = null,

        @SerializedName("rating")
        var rating: Double? = null,

        @SerializedName("metroStation")
        var metroStation: MetroStation? = null,

        @SerializedName("averageBill")
        var averageBill: String? = null,

        @SerializedName("tags")
        var tags: List<String>? = null

)