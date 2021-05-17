package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainContent (

        @SerializedName("categoryList")
        var categoryList: List<Category>

) : Serializable