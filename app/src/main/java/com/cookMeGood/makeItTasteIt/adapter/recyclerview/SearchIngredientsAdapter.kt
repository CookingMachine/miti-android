package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnSearchIngredientClickListener
import com.cookMeGood.makeItTasteIt.api.dto.Ingredient
import kotlinx.android.synthetic.main.item_search_ingredient.view.*

class SearchIngredientsAdapter(private val ingredients: List<Ingredient>,
                               val listener: OnSearchIngredientClickListener) :
        RecyclerView.Adapter<SearchIngredientsAdapter.ViewHolder>() {

    private var clickedIngredientsList = arrayListOf<Ingredient>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ingredientName = view.ingredientName!!
        var checkBox = view.ingredientCheckBox!!
        var layout = view.ingredientLayout!!
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
        val ingredient = ingredients[position]

        holder.ingredientName.text = ingredient.name
        holder.layout.setOnClickListener {
            if (holder.checkBox.visibility == View.VISIBLE){
                holder.checkBox.visibility = View.GONE
                clickedIngredientsList.remove(ingredient)
            }
            else {
                holder.checkBox.visibility = View.VISIBLE
                clickedIngredientsList.add(ingredient)
            }
            listener.onClick(clickedIngredientsList)
        }
    }

    override fun getItemCount(): Int = ingredients.size
}