package com.cookMeGood.makeItTasteIt.adapter.listener

interface SuggestIngredientEditListener {
    fun editIngredient(name: String,amount: String,position: Int)
    fun removeIngredient(position: Int)
}