package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse (

    @SerializedName("jwtToken")
    var jwtToken: String? = null

): Serializable