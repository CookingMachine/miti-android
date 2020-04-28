package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.CartRecipeAdapter
import com.cookMeGood.makeItTasteIt.data.dto.Recipe
import kotlinx.android.synthetic.main.f_cart.*

class CartFragment: SuperFragment() {

    private val recipes: List<Recipe> = arrayListOf()

    private val cartAdapter: CartRecipeAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(cartFragmentToolbar)
        cartFragmentToolbar.title = getString(R.string.title_cart)
    }

    override fun setAttr() {
        setLayout(R.layout.f_cart)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}