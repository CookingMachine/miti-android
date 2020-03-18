package com.shveed.cookmegood.data.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data



@Data
@JsonIgnoreProperties("id", "recipeList")
class Category {
    @JsonProperty("name")
    var name: String? = null
}