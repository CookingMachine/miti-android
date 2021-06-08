package com.api.dto.request

import com.api.dto.CalorieContent
import com.api.dto.ContextIngredient
import com.api.dto.Step
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

    @SerializedName("authorId")
    var authorId: Long? = null,

    @SerializedName("category")
    var category: String? = null,

    @SerializedName("contextIngredientList")
    var contextIngredientList: ArrayList<ContextIngredient>? = null,

    @SerializedName("calorie")
    var calorieContent: CalorieContent? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("steps")
    var steps: ArrayList<Step>? = arrayListOf()

) : Serializable
