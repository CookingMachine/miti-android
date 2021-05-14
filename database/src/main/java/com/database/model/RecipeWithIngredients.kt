package com.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded var recipe: RecipeModel,
    @Relation(
            parentColumn = "recipeId",
            entityColumn = "ingredientId"
    )
    var ingredients: List<IngredientModel>
)