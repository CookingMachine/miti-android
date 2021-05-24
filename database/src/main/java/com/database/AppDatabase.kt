package com.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.database.dao.IngredientDao
import com.database.dao.RecipeDao
import com.database.model.IngredientModel
import com.database.model.RecipeModel

@Database(entities = [RecipeModel::class, IngredientModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
}
