package com.database.dao

import androidx.room.*
import com.database.model.IngredientModel

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: IngredientModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredients: List<IngredientModel>)

    @Update
    fun update(ingredient: IngredientModel)

    @Delete
    fun delete(ingredient: IngredientModel)
}