package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.utils.TimeConverter
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = recipes[position]

        holder.image.setImageResource(R.drawable.image_recipe_background)
        holder.name.text = recipe.name
        holder.kitchen.text = recipe.kitchen
        if (!recipe.time.isNullOrEmpty()) {
            holder.time.text = TimeConverter.convertSecondsToTimeFormat(recipe.time!!.toInt())
        } else {
            holder.time.visibility = View.GONE
        }
        if (!recipe.rating.isNullOrEmpty()) {
            holder.rating.text = "Рейтинг: ${recipe.rating}"
        } else {
            holder.rating.visibility = View.GONE
        }

        holder.categoryRecipeLayout.setOnClickListener {
            changeListener.openRecipe(recipe)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.categoryPageRecipeImage!!
        val name = view.categoryPageRecipeTitle!!
        val kitchen = view.categoryPageRecipeNationality!!
        val time = view.categoryPageRecipeTime!!
        val rating = view.categoryPageRecipeRating!!
        val categoryRecipeLayout = view.categoryPageRecipeLayout!!
    }
}