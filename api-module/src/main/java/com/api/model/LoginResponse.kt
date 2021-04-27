package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class LoginResponse (

    @JsonProperty("jwtToken")
    var jwtToken: String? = null

): Serializable