package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.dto.ContextIngredient
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.item_ingredient.view.*

class RecipePageIngredientsListAdapter(private var ingredients: List<ContextIngredient>) :
    RecyclerView.Adapter<RecipePageIngredientsListAdapter.ViewHolder>() {

    private var portionsValue: Short = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ingredient,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currIngredient = ingredients[position]

        holder.name.text = currIngredient.ingredient!!.name!!
        holder.amount.text =
            "${(currIngredient.amount!! * portionsValue)} ${currIngredient.measure!!.value}"
    }

    fun updatePortionsValueAndListData(value: Short) {
        portionsValue = value
        notifyDataSetChanged()
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name = item.ingredientName!!
        var amount = item.ingredientAmount!!
    }
}