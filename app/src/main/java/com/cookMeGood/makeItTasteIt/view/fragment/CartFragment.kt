package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CartRecipeListAdapter
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.dto.Recipe
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment: SuperFragment() {

    private val recipes = arrayListOf<Recipe>()
    private var cartListAdapter: CartRecipeListAdapter? = null

    override fun setAttr() = setLayout(R.layout.fragment_cart)

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_cart)

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_fall_down)

        setCartData()

        cartListAdapter = CartRecipeListAdapter(recipes, requireContext())
        cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartRecycler.layoutAnimation = animation
        cartRecycler.adapter = cartListAdapter

    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun setCartData(){
        if (recipes.isEmpty()) {
            recipes.add(Recipe(null,"Пицца", "Для большой компании", null, null, Category(),"2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
            recipes.add(Recipe(null,"Борщ", "Хватит на всю семью", null, null, Category(), "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
        }
    }


}