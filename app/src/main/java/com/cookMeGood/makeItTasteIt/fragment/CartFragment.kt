package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.App
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.listener.OnCartUpdateListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CartRecipeListAdapter
import com.database.AppDatabase
import com.database.model.RecipeModel
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : SuperFragment() {
    private var cartListAdapter: CartRecipeListAdapter? = null
    private var database: AppDatabase? = null

    override fun setAttr() = setLayout(R.layout.fragment_cart)

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_cart)

        database = App.instance.getDataBase()
        database!!.recipeDao().getAll()
                .observe(this, {
                    if (cartListAdapter == null) initListAdapter(it)
                    else cartListAdapter!!.updateList(it)
                })
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun initListAdapter(recipes: List<RecipeModel>) {
        val animation = AnimationUtils.loadLayoutAnimation(
                context, R.anim.anim_layout_list_fall_down
        )
        cartListAdapter = CartRecipeListAdapter (recipes, childFragmentManager)
        cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartRecycler.layoutAnimation = animation
        cartRecycler.adapter = cartListAdapter
    }

}