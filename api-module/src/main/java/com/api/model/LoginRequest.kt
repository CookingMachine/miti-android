package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class LoginRequest (

        @JsonProperty("username")
        private var username: String? = null,

        @JsonProperty("password")
        private var password: String? = null

): Serializable