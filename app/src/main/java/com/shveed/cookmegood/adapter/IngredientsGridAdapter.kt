package com.shveed.cookmegood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shveed.cookmegood.R
import com.shveed.cookmegood.data.dto.Ingredient
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsGridAdapter(val context: Context, var ingredients: List<Ingredient>): RecyclerView.Adapter<IngredientsGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_ingredient,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currIngredient = ingredients[position]

        holder.name.text = currIngredient.name
        holder.amount.text = currIngredient.amount
    }

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var name = item.ingredientName!!
        var amount = item.ingredientAmount!!
        var addButton = item.addIngredientButton!!
    }
}