package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.FavouritesListAdapter
import com.cookMeGood.makeItTasteIt.api.dto.Category
import com.cookMeGood.makeItTasteIt.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment: SuperFragment() {

    private var recipeList = arrayListOf<Recipe>()
    private var recipeListAdapter: FavouritesListAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_favourites)

        getRecipesFromServer()

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_fall_down)

        recipeListAdapter = FavouritesListAdapter(recipeList)
        favouritesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favouritesList.layoutAnimation = animation
        favouritesList.adapter = recipeListAdapter
    }

    override fun setAttr() = setLayout(R.layout.fragment_favourites)

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun getRecipesFromServer(){
        if (recipeList.isEmpty()) {
            recipeList.add(Recipe(null,"Пицца", "Для большой компании", null, null, Category(),"2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"))
            recipeList.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipeList.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipeList.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
        }
    }
}