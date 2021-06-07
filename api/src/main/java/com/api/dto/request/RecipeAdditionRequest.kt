package com.api.dto.request

import com.api.dto.CalorieContent
import com.api.dto.Category
import com.api.dto.ContextIngredient
import com.api.dto.Step
import com.api.dto.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeAdditionRequest(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("time")
    var time: String? = null,

    @SerializedName("kitchen")
    var kitchen: String? = null,

    @SerializedName("author")
    var author: User? = null,

    @SerializedName("category")
    var category: Category? = null,

    @SerializedName("contextIngredientList")
    var contextIngredientList: ArrayList<ContextIngredient>? = null,

    @SerializedName("calorie")
    var calorieContent: CalorieContent? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("steps")
    var steps: ArrayList<Step>? = arrayListOf()

) : Serializable
