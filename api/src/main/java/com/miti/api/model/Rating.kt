package com.miti.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Rating(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("value")
        var value: Int? = null,

        @JsonProperty("recipe")
        var recipe: Recipe? = null,

        @JsonProperty("user")
        var user: User? = null
): Serializable
