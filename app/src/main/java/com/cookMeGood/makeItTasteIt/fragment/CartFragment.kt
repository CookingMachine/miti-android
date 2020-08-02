package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.CartRecipeAdapter
import com.cookMeGood.makeItTasteIt.dto.Recipe
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment: SuperFragment() {

    private val recipes = arrayListOf<Recipe>()

    private var cartAdapter: CartRecipeAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(cartFragmentToolbar)
        cartFragmentToolbar.title = getString(R.string.title_cart)

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_list_fall_down)

        setCartData()

        cartAdapter = CartRecipeAdapter(recipes, requireContext())
        cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartRecycler.layoutAnimation = animation
        cartRecycler.adapter = cartAdapter

    }

    override fun setAttr() {
        setLayout(R.layout.fragment_cart)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun setCartData(){
        if(recipes.isEmpty()) {
            recipes.add(Recipe(null,"Пицца", "Для большой компании", null, null, "2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
        }
    }


}