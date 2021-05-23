package com.api.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRegistrationResponse(

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("jwtToken")
    var jwtToken: String? = null

): Serializable
