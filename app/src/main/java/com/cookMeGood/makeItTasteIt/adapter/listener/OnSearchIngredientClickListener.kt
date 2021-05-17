package com.cookMeGood.makeItTasteIt.adapter.listener

import com.api.dto.Ingredient

interface OnSearchIngredientClickListener {

    fun onClick(ingredients: List<Ingredient>)
}