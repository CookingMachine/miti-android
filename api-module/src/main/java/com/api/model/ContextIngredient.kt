package com.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContextIngredient(

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("amount")
        var amount: Long? = null,

        @SerializedName("measure")
        var measure: Measure? = null,

        @SerializedName("ingredient")
        var ingredient: Ingredient? = null,

        @SerializedName("recipe")
        var recipe: Recipe? = null

): Serializable