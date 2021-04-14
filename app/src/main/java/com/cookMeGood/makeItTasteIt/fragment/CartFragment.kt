package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CartRecipeListAdapter
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.api.model.Recipe
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment: SuperFragment() {

    private var recipeList = listOf<Recipe>()
    private var cartListAdapter: CartRecipeListAdapter? = null

    override fun setAttr() = setLayout(R.layout.fragment_cart)

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_cart)

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_fall_down)

        recipeList = HelpUtils.getStubRecipeList()

        cartListAdapter = CartRecipeListAdapter(recipeList, requireContext())
        cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartRecycler.layoutAnimation = animation
        cartRecycler.adapter = cartListAdapter

    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

}