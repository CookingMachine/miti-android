package com.cookMeGood.makeItTasteIt.adapter.listener

import com.cookMeGood.makeItTasteIt.api.dto.Recipe

interface OnOpenRecipeListener {

    fun openRecipe(recipe: Recipe)

}