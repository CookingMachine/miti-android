package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainContent (

        @SerializedName("recipeOfTheDay")
        var recipeOfTheDay: Recipe? = null,

        @SerializedName("categoryList")
        var categoryList: List<Category>? = null,

        @SerializedName("lowCalories")
        var lowCalorieRecipes: List<Recipe>? = null,

        @SerializedName("fastAndDelicious")
        var fastAndDeliciousRecipes: List<Recipe>? = null

): Serializable