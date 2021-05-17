package com.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RECIPE")
data class RecipeModel (
        @PrimaryKey(autoGenerate = false)
        var recipeid: Long? = null,

        var name: String? = null,

        var kitchen: String? = null,

        var category: String? = null,

        var image: String? = null
)