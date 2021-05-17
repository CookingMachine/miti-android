package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class User(

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("username")
        var username: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("password")
        var password: String? = null,

        @SerializedName("email")
        var email: String? = null,

        @SerializedName("status")
        var status: Boolean? = null,

        @SerializedName("role")
        var role: String? = null,

        @SerializedName("registrationDate")
        var registrationDate: Date? = null,

        @SerializedName("lastAuthDate")
        var lastAuthDate: Date? = null,

        @SerializedName("recipeList")
        var recipeList: List<Recipe>? = null,

        @SerializedName("commentList")
        var commentList: List<Comment>? = null,

        @SerializedName("favouriteList")
        var favouriteList: List<Recipe>? = null,

        @SerializedName("rating")
        var rating: Rating? = null

): Serializable