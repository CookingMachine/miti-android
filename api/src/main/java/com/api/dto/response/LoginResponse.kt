package com.api.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse (

    @SerializedName("jwtToken")
    var jwtToken: String? = null

): Serializable