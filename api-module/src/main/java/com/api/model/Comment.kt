package com.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Comment(

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("comment")
        var comment: String? = null,

        @SerializedName("createDate")
        var createDate: Date? = null,

        @SerializedName("editDate")
        var editDate: Date? = null,

        @SerializedName("commentator")
        var commentator: User? = null,

        @SerializedName("recipe")
        var recipe: Recipe? = null

): Serializable
