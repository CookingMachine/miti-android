package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.CartRecipeAdapter
import com.cookMeGood.makeItTasteIt.data.dto.Recipe
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.item_cart_recipe.*

class CartFragment: SuperFragment() {

    private val recipes = arrayListOf<Recipe>()

    private var cartAdapter: CartRecipeAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(cartFragmentToolbar)
        cartFragmentToolbar.title = getString(R.string.title_cart)

        setCartData()

        cartAdapter = CartRecipeAdapter(recipes, context!!)
        cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartRecycler.adapter = cartAdapter

    }

    override fun setAttr() {
        setLayout(R.layout.fragment_cart)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun setCartData(){
        recipes.add(Recipe("Пицца", "Для большой компании", "2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"))
        recipes.add(Recipe("Борщ", "Хватит на всю семью", "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
    }


}