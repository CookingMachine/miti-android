package com.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.database.model.RecipeModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeModel): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<RecipeModel>)

    @Update
    fun update(recipe: RecipeModel)

    @Delete
    fun delete(recipe: RecipeModel): Single<Int>

    @Query("SELECT * FROM RECIPE")
    fun getAll(): LiveData<List<RecipeModel>>
}
