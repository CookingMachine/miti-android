package com.miti.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class Comment(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("comment")
        var comment: String? = null,

        @JsonProperty("createDate")
        var createDate: Date? = null,

        @JsonProperty("editDate")
        var editDate: Date? = null,

        @JsonProperty("commentator")
        var commentator: User? = null,

        @JsonProperty("recipe")
        var recipe: Recipe? = null

): Serializable
