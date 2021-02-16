package com.cookMeGood.makeItTasteIt.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Category (

    @JsonProperty("id")
    var id: String? = null,

    @JsonProperty("name")
    var name: String? = null

) : Serializable