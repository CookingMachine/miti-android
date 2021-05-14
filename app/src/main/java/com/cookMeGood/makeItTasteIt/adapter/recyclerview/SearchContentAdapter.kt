package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.api.dto.Recipe
import kotlinx.android.synthetic.main.item_search_content.view.*

class SearchContentAdapter(private val recipes: List<Recipe>, val context: Context,
                           var changeListener: OnOpenRecipeListener):
        RecyclerView.Adapter<SearchContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.item_search_content,
                                parent, false
                        )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.recipeTitle.text = recipe.name
        holder.recipeKitchen.text = recipe.kitchen

        holder.layout.setOnClickListener {
            changeListener.openRecipe(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val layout = view.searchContentLayout!!
        val recipeTitle = view.searchContentTitle!!
        val recipeKitchen = view.searchContentKitchen!!
        val recipeTimeValue = view.searchContentTime!!
    }

}