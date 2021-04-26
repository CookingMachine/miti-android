package com.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequest (

        @SerializedName("username")
        private var username: String? = null,

        @SerializedName("password")
        private var password: String? = null

): Serializable