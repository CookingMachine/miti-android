package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.dto.Recipe
import kotlinx.android.synthetic.main.item_favourites_recipe.view.*

class FavouritesListAdapter(
        private var recipeList: List<Recipe>
): RecyclerView.Adapter<FavouritesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipePicture = view.favouritesImage!!
        val recipeName = view.favouritesTitle!!
        val recipeKitchen = view.favouritesNationality!!
        val recipeRating = view.favouritesRatingBar!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_favourites_recipe, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipeList[position]

        //holder.recipePicture
        holder.recipeName.text = recipe.name
        holder.recipeKitchen.text = "${recipe.kitchen} кухня"
        //holder.recipeRating
    }

    override fun getItemCount(): Int = recipeList.size
}