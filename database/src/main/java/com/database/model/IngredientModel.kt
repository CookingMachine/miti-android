package com.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INGREDIENT")
data class IngredientModel (

        @PrimaryKey(autoGenerate = true)
        val ingredientId: Int? = null,

        val name: String? = null,

        val measure: String? = null,

        val amount: String? = null
)