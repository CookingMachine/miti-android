package com.cookMeGood.makeItTasteIt.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Recipe(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("description")
        var description: String? = null,

        @JsonProperty("author")
        var author: User? = null,

        @JsonProperty("category")
        var category: Category? = null,

        @JsonProperty("time")
        var time: String? = null,

        @JsonProperty("image")
        var image: String? = null,

        @JsonProperty("kitchen")
        var kitchen: String? = null
): Serializable