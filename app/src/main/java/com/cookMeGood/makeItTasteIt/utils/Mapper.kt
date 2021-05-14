package com.cookMeGood.makeItTasteIt.utils

import com.api.dto.ContextIngredient
import com.api.dto.Ingredient
import com.api.dto.Recipe
import com.database.model.IngredientModel
import com.database.model.RecipeModel

/**
 * Класс для реализации маппинга DTO объектов в модели базы данных
 */
object Mapper {

    /**
     * Маппинг объекта Recipe в сущность базы данных Recipe
     *
     * @param recipe dto-объект рецепта
     * @return
     */
    fun recipeDtoToRecipeModel(recipe: Recipe): RecipeModel =
            RecipeModel(recipe.id!!, recipe.name, recipe.kitchen, recipe.category!!.name,
                    null/*, contextIngredientDtoToIngredientModel(recipe.contextIngredientList!!)*/)

    private fun contextIngredientDtoToIngredientModel(
            contextIngredient: ContextIngredient): IngredientModel =
            IngredientModel(0, contextIngredient.ingredient!!.name!!,
                    contextIngredient.measure!!.name, contextIngredient.amount.toString())

    private fun contextIngredientDtoToIngredientModel(
            contextIngredients: ArrayList<ContextIngredient>): List<IngredientModel> =
            contextIngredients.map { contextIngredientDtoToIngredientModel(it) }
}