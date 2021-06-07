package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.App
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CartRecipeListAdapter
import com.cookMeGood.makeItTasteIt.container.AnimationContainer
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
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
            .observe(
                this,
                {
                    if (cartListAdapter == null) initListAdapter(it)
                    else cartListAdapter!!.updateList(it)
                }
            )
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit

    private fun initListAdapter(recipes: List<RecipeModel>) {
        if (cartListAdapter == null) {
            cartListAdapter = CartRecipeListAdapter(recipes, childFragmentManager)
            cartRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            cartRecycler.layoutAnimation = AnimationContainer(requireContext()).ANIM_FALL_DOWN
            cartRecycler.adapter = cartListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as SuperActivity).supportActionBar!!.elevation =
            ContextUtils.convertDpToPixel(4F, requireContext())
    }
}
