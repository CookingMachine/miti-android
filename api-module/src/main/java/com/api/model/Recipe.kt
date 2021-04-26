package com.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Recipe(

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("description")
        var description: String? = null,

        @SerializedName("time")
        var time: String? = null,

        @SerializedName("createDate")
        var createDate: Date? = null,

        @SerializedName("kitchen")
        var kitchen: String? = null,

        @SerializedName("author")
        var author: User? = null,

        @SerializedName("category")
        var category: Category? = null,

        @SerializedName("commentList")
        var commentList: List<Comment>? = null,

        @SerializedName("contextIngredientList")
        var contextIngredientList: ContextIngredient? = null,

        @SerializedName("calorie")
        var calorieContent: CalorieContent? = null,

        @SerializedName("favouriteUsers")
        var favouriteUsers: List<User>? = null,

        @SerializedName("rating")
        var rating: List<Rating>? = null,

        @SerializedName("image")
        var image: String? = null

): Serializable