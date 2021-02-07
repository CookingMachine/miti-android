package com.cookMeGood.makeItTasteIt.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties("role", "")
data class User(

        @JsonProperty("username")
        var username: String? = null,

        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("password")
        var password: String? = null,

        @JsonProperty("email")
        var email: String? = null,

        @JsonProperty("role")
        var role: Int? = null

): Serializable