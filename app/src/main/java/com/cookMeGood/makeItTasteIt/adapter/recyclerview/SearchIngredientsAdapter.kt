package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.dto.Ingredient
import kotlinx.android.synthetic.main.item_search_ingredient.view.*

class SearchIngredientsAdapter(private val ingredients: List<Ingredient>):
        RecyclerView.Adapter<SearchIngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var ingredientName = view.ingredientName!!
        var checkBox = view.ingredientCheckBox!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(
                                R.layout.item_search_ingredient,
                                parent, false
                        )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientName.text = ingredients[position].name
    }

    override fun getItemCount(): Int = ingredients.size
}