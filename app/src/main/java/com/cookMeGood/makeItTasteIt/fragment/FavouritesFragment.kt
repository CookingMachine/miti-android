package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.ApiService
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.App
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.FavouritesListAdapter
import com.cookMeGood.makeItTasteIt.container.AnimationContainer
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.container.IntentContainer
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.database.AppDatabase
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favourites.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesFragment : SuperFragment() {

    private lateinit var database: AppDatabase

    private var recipeList: List<Recipe>? = null
    private var recipeListAdapter: FavouritesListAdapter? = null

    private val onOpenRecipeListener = object : OnOpenRecipeListener {
        override fun openRecipe(recipe: Recipe) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(IntentContainer.INTENT_RECIPE, recipe)
            startActivity(intent)
        }
    }

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_favourites)

        database = App.instance.getDataBase()
        recipeList = ContextUtils.getStubRecipeList()

        favouritesFragmentProgressBar.visibility = View.VISIBLE
        favouritesRecipeList.visibility = View.GONE

        getFavouritesListFromServer(DataContainer.currentUser!!.id!!)

        favouritesTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
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

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun initOrUpdateAdapter() {
        if (recipeListAdapter == null) {
            recipeListAdapter =
                FavouritesListAdapter(recipeList!!, onOpenRecipeListener, childFragmentManager)
            favouritesRecipeList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            favouritesRecipeList.layoutAnimation =
                AnimationContainer(requireContext()).ANIM_FALL_DOWN
            favouritesRecipeList.adapter = recipeListAdapter
        } else {
            recipeListAdapter!!.updateList(recipeList!!)
        }
    }

    override fun setAttr() = setLayout(R.layout.fragment_favourites)

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit

    override fun onResume() {
        getFavouritesListFromServer(DataContainer.currentUser!!.id!!)
        super.onResume()
    }

    private fun getFavouritesListFromServer(userId: Long) {
        ApiService.getApi().getFavouritesByUserId(userId)
            .enqueue(object : Callback<List<Recipe>> {
                override fun onResponse(
                    call: Call<List<Recipe>>,
                    response: Response<List<Recipe>>
                ) {
                    if (response.isSuccessful) {
                        recipeList = response.body()
                        if (recipeList!!.isNotEmpty()) {
                            initOrUpdateAdapter()
                        } else {
                            // TODO: показывать заглушку
                        }
                    }
                    favouritesFragmentProgressBar.visibility = View.GONE
                    favouritesRecipeList.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                    ContextUtils.goLongToast(requireContext(), t.message.toString())
                }
            })
    }
}
