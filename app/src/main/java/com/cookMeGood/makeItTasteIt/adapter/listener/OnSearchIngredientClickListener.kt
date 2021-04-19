package com.cookMeGood.makeItTasteIt.adapter.listener

import com.miti.api.model.Ingredient

interface OnSearchIngredientClickListener {

    fun onClick(ingredients: List<Ingredient>)
}