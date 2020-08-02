package com.cookMeGood.makeItTasteIt.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties("role", "")
data class User(
        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("username")
        var username: String? = null,

        @JsonProperty("password")
        var password: String? = null,

        @JsonProperty("email")
        var email: String? = null,

        @JsonProperty("isActive")
        var isActive: Boolean? = null
): Serializable