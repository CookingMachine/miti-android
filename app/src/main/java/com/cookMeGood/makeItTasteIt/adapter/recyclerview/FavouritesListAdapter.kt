package com.cookMeGood.makeItTasteIt.adapter.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.FavouritesRemoveDialogAdapter
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.listener.OnUpdateFavouritesListener
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.utils.TimeConverter
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesImage
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesLayout
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesNationality
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesRecipeTime
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesRemoveButton
import kotlinx.android.synthetic.main.item_favourites_recipe.view.favouritesTitle

class FavouritesListAdapter(
    private var recipeList: List<Recipe>,
    private var onOpenRecipeListener: OnOpenRecipeListener,
    var supportFragmentManager: FragmentManager
) : RecyclerView.Adapter<FavouritesListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var favouritesDialogAdapter: FavouritesRemoveDialogAdapter? = null

    private val onRemoveElement = object : OnUpdateFavouritesListener {
        override fun update(recipe: Recipe) {
            val itemPosition = recipeList.indexOf(recipe)
            (recipeList as ArrayList).remove(recipe)
            notifyItemRemoved(itemPosition)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipePicture = view.favouritesImage!!
        val recipeName = view.favouritesTitle!!
        val recipeKitchen = view.favouritesNationality!!
        val recipeLayout = view.favouritesLayout!!
        val recipeTime = view.favouritesRecipeTime!!
        val recipeDelete = view.favouritesRemoveButton!!
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
        //TODO: выставить картинку
        holder.recipeName.text = recipe.name
        holder.recipeKitchen.text = "${recipe.kitchen} кухня"
        holder.recipeTime.text = TimeConverter.convertSecondsToTimeFormat(recipe.time!!.toInt())

        holder.recipeLayout.setOnClickListener {
            onOpenRecipeListener.openRecipe(recipe)
        }
        holder.recipeDelete.setOnClickListener {
            if (favouritesDialogAdapter == null) {
                favouritesDialogAdapter = FavouritesRemoveDialogAdapter(
                    DataContainer.currentUser!!.id!!,
                    recipe,
                    onRemoveElement
                )
            }
            favouritesDialogAdapter!!.show(supportFragmentManager, "Favourites Dialog")
        }
    }

    override fun getItemCount(): Int = recipeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(recipes: List<Recipe>) {
        recipeList = recipes
        notifyDataSetChanged()
    }
}
