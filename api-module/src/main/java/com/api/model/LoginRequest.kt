package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest (

        @JsonProperty("username")
        private var username: String? = null,

        @JsonProperty("password")
        private var password: String? = null

)