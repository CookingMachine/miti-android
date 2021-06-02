package com.cookMeGood.makeItTasteIt.adapter.listener

import com.api.dto.Recipe

interface OnUpdateFavouritesListener {
    fun update(recipe: Recipe)
}