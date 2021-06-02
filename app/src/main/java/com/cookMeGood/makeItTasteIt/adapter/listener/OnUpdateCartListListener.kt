package com.cookMeGood.makeItTasteIt.adapter.listener

import com.database.model.RecipeModel

interface OnUpdateCartListListener {
    fun update(recipe: RecipeModel)
}