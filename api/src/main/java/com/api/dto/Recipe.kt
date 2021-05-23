package com.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class Recipe (

        @SerializedName("id")
        var id: Long? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("description")
        var description: String? = null,

        @SerializedName("time")
        var time: String? = null,

        @SerializedName("createDate")
        var createDate: String? = null,

        @SerializedName("kitchen")
        var kitchen: String? = null,

        @SerializedName("author")
        var author: User? = null,

        @SerializedName("category")
        var category: Category? = null,

        @SerializedName("commentList")
        var commentList: List<Comment>? = null,

        @SerializedName("contextIngredientList")
        var contextIngredientList: ArrayList<ContextIngredient>? = null,

        @SerializedName("calorie")
        var calorieContent: CalorieContent? = null,

        @SerializedName("favouriteUsers")
        var favouriteUsers: List<User>? = null,

        @SerializedName("rating")
        var rating: List<Rating>? = null,

        @SerializedName("image")
        var image: String? = null,

        @SerializedName("steps")
        var steps: ArrayList<Step>? = arrayListOf()

): Serializable