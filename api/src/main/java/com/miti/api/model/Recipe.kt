package com.miti.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class Recipe(

        @JsonProperty("id")
        var id: Long? = null,

        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("description")
        var description: String? = null,

        @JsonProperty("time")
        var time: String? = null,

        @JsonProperty("createDate")
        var createDate: Date? = null,

        @JsonProperty("kitchen")
        var kitchen: String? = null,

        @JsonProperty("author")
        var author: User? = null,

        @JsonProperty("category")
        var category: Category? = null,

        @JsonProperty("commentList")
        var commentList: List<Comment>? = null,

        @JsonProperty("contextIngredientList")
        var contextIngredientList: ContextIngredient? = null,

        @JsonProperty("calorie")
        var calorieContent: CalorieContent? = null,

        @JsonProperty("favouriteUsers")
        var favouriteUsers: List<User>? = null,

        @JsonProperty("rating")
        var rating: List<Rating>? = null,

        @JsonProperty("image")
        var image: String? = null

): Serializable