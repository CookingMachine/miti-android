package com.cookMeGood.makeItTasteIt.adapter.listener

import com.api.model.Ingredient

interface OnSearchIngredientClickListener {

    fun onClick(ingredients: List<Ingredient>)
}