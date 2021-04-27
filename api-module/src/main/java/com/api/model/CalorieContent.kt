package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class CalorieContent(
        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("calories")
        var calories: Long? = null,

        @JsonProperty("protein")
        var protein: Long? = null,

        @JsonProperty("fat")
        var fat: Long? = null,

        @JsonProperty("carbohydrates")
        var carbohydrates: Long? = null
): Serializable
