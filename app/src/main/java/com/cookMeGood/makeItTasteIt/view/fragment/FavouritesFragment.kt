package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.FavouritesListAdapter
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import com.google.android.material.tabs.TabLayout
import com.miti.api.model.Recipe
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment: SuperFragment() {

    private var recipeList = listOf<Recipe>()
    private var recipeListAdapter: FavouritesListAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_favourites)

        recipeList = HelpUtils.getStubRecipeList()

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_fall_down)

        recipeListAdapter = FavouritesListAdapter(recipeList)
        favouritesRecipeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favouritesRecipeList.layoutAnimation = animation
        favouritesRecipeList.adapter = recipeListAdapter

        favouritesTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position){
                    0 -> {
                        if (favouritesRecipeList.visibility == View.GONE) {
                            favouritesRecipeList.visibility = View.VISIBLE
                            favouritesRestaurantList.visibility = View.GONE
                        }
                    }
                    1 -> {
                        if (favouritesRestaurantList.visibility == View.GONE) {
                            favouritesRestaurantList.visibility = View.VISIBLE
                            favouritesRecipeList.visibility = View.GONE
                        }
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun setAttr() = setLayout(R.layout.fragment_favourites)

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}