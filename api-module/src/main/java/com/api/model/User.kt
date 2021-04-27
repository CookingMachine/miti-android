package com.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties("role", "")
data class User(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("username")
        var username: String? = null,

        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("password")
        var password: String? = null,

        @JsonProperty("email")
        var email: String? = null,

        @JsonProperty("status")
        var status: Boolean? = null,

        @JsonProperty("role")
        var role: Int? = null,

        @JsonProperty("registrationDate")
        var registrationDate: Date? = null,

        @JsonProperty("lastAuthDate")
        var lastAuthDate: Date? = null,

        @JsonProperty("recipeList")
        var recipeList: List<Recipe>? = null,

        @JsonProperty("commentList")
        var commentList: List<Comment>? = null,

        @JsonProperty("favouriteList")
        var favouriteList: List<Recipe>? = null,

        @JsonProperty("rating")
        var rating: Rating? = null

): Serializable