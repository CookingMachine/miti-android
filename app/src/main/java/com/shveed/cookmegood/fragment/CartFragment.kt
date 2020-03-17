package com.shveed.cookmegood.fragment

import android.content.Intent
import android.view.View
import com.shveed.cookmegood.R
import com.shveed.cookmegood.activity.SuperActivity
import com.shveed.cookmegood.adapter.CartRecipeAdapter
import com.shveed.cookmegood.data.dto.Recipe
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