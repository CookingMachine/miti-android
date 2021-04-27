package com.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class ContextIngredient(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("amount")
        var amount: Long? = null,

        @JsonProperty("measure")
        var measure: Measure? = null,

        @JsonProperty("ingredient")
        var ingredient: Ingredient? = null,

        @JsonProperty("recipe")
        var recipe: Recipe? = null

): Serializable