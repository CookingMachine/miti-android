package com.miti.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class MainContent (

        @JsonProperty("categoryList")
        var categoryList: List<Category>

) : Serializable