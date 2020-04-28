package com.cookMeGood.makeItTasteIt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.data.dto.Recipe
import kotlinx.android.synthetic.main.item_category_recipe.view.*

class CategoryRecipeAdapter(private val recipes: List<Recipe>, val context: Context?): RecyclerView.Adapter<CategoryRecipeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.item_category_recipe,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipes[position]

        holder.image.setImageResource(recipe.image!!.toInt())
        holder.name.text = recipe.name
        holder.kitchen.text = recipe.kitchen
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.recipeImage!!
        val name = view.recipeTitle!!
        val kitchen = view.recipeNationality!!
    }
}