package com.cookMeGood.makeItTasteIt.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Restaurant (

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("title")
        var title: String? = null,

        @JsonProperty("destination")
        var destination: String? = null,

        @JsonProperty("rating")
        var rating: Double? = null,

        @JsonProperty("tags")
        var tags: List<String>? = null

)