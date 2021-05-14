package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import kotlinx.android.synthetic.main.item_favourites_recipe.view.*

class FavouritesListAdapter(
        private var recipeList: List<Recipe>
) : RecyclerView.Adapter<FavouritesListAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipePicture = view.favouritesImage!!
        val recipeName = view.favouritesTitle!!
        val recipeKitchen = view.favouritesNationality!!
        val recipeLayout = view.favouritesLayout!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.item_favourites_recipe, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipeList[position]

        if (position == 0) {
            val margin16 = ContextUtils.convertDpToPixel(16, context)
            val margin64 = ContextUtils.convertDpToPixel(64, context)
            val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(margin16, margin64, margin16, 0)

            holder.recipeLayout.layoutParams = params
        }

        //holder.recipePicture
        holder.recipeName.text = recipe.name
        holder.recipeKitchen.text = "${recipe.kitchen} кухня"
        //holder.recipeRating
    }

    override fun getItemCount(): Int = recipeList.size
}