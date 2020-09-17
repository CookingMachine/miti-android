package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.dto.Ingredient
import kotlinx.android.synthetic.main.item_cart_ingredient.view.*

class CartIngredientListAdapter(private val context: Context,
                                private val ingredients: ArrayList<Ingredient>) :
        RecyclerView.Adapter<CartIngredientListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_cart_ingredient,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ingredient = ingredients[position]

        holder.name.text = ingredient.name
        holder.amount.text = ingredient.amount

        holder.remove.setOnClickListener {
            if(holder.cancelLine.visibility == View.VISIBLE){
                holder.cancelLine.visibility = View.GONE
                holder.chechBox.isChecked = false
            }
            else{
                holder.cancelLine.visibility = View.VISIBLE
                holder.chechBox.isChecked = true
            }
            notifyDataSetChanged()
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.cartIngredientName!!
        val amount = view.cartIngredientAmount!!
        val remove = view.ingredientCartIcon!!
        val cancelLine = view.cartIngredientCancelLine!!
        val chechBox = view.cartIngredientCheckBox!!

    }
}