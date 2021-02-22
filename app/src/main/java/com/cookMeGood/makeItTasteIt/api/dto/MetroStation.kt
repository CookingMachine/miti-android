package com.cookMeGood.makeItTasteIt.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MetroStation(

        @JsonProperty("id")
        var id: Int? = null,

        @JsonProperty("title")
        var title: String? = null
)