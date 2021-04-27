package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse (

    @JsonProperty("jwtToken")
    var jwtToken: String? = null

)