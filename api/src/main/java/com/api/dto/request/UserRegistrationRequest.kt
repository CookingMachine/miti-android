package com.api.dto.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRegistrationRequest(

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("name")
    var name: String? = null

): Serializable
