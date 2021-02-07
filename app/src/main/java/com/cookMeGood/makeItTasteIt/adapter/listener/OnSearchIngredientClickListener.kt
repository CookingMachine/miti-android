package com.cookMeGood.makeItTasteIt.adapter.listener

import com.cookMeGood.makeItTasteIt.api.dto.Ingredient

interface OnSearchIngredientClickListener {

    fun onClick(ingredients: List<Ingredient>)
}