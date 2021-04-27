package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Restaurant (

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("title")
        var title: String? = null,

        @JsonProperty("kitchen")
        var kitchen: String? = null,

        @JsonProperty("destination")
        var destination: String? = null,

        @JsonProperty("rating")
        var rating: Double? = null,

        @JsonProperty("metroStation")
        var metroStation: MetroStation? = null,

        @JsonProperty("averageBill")
        var averageBill: String? = null,

        @JsonProperty("tags")
        var tags: List<String>? = null

)