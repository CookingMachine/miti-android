package com.cookMeGood.makeItTasteIt.listener

import com.cookMeGood.makeItTasteIt.dto.Recipe

interface OnOpenRecipeListener {

    fun openRecipe(recipe: Recipe)

}