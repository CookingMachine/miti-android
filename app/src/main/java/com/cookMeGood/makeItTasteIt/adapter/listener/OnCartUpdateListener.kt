package com.cookMeGood.makeItTasteIt.adapter.listener

import com.database.model.RecipeModel

interface OnCartUpdateListener {
    fun update(recipe: RecipeModel)
}