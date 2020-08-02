package com.cookMeGood.makeItTasteIt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.dto.Recipe
import com.cookMeGood.makeItTasteIt.listener.OnOpenRecipeListener
import kotlinx.android.synthetic.main.item_category_recipe.view.*

class RecipeListAdapter(private var recipes: List<Recipe>, val context: Context?,
                        private val changeListener: OnOpenRecipeListener):
        RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
                        R.layout.item_category_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipes[position]

        holder.image.setImageResource(R.drawable.image_recipe_background)
        holder.name.text = recipe.name
        holder.kitchen.text = recipe.kitchen

        holder.cardLayout.setOnClickListener {
            changeListener.openRecipe(recipe)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.recipeImage!!
        val name = view.recipeTitle!!
        val kitchen = view.recipeNationality!!
        val cardLayout = view.categoryRecipeLayout!!
    }

    fun onUpdateList(newList: List<Recipe>) {
        this.recipes = newList
        notifyDataSetChanged()
    }
}