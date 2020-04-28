package com.cookMeGood.makeItTasteIt.data.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("id", "avatar", "favouriteList", "ratingList")
class User {

    @JsonProperty("login")
    private val login: String? = null

    @JsonProperty("password")
    private val password: String? = null

    @JsonProperty("avatar")
    private val avatar: String? = null

    @JsonProperty("countRecipe")
    private val countRecipe = 0

    @JsonProperty("avgRating")
    private val avgRating = 0f
}