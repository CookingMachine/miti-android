package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.CategoryRecipeAdapter
import com.cookMeGood.makeItTasteIt.adapter.RecipeItemClickListener
import com.cookMeGood.makeItTasteIt.data.dto.Recipe
import com.cookMeGood.makeItTasteIt.listener.OnOpenRecipeListener
import kotlinx.android.synthetic.main.f_category.*

class CategoryFragment: SuperFragment() {

    private val recipes = arrayListOf<Recipe>()

    private val openRecipeListener = object: OnOpenRecipeListener{
        override fun openRecipe(name: String, image: Int) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("recipeName", name)
            intent.putExtra("recipeImage", image)
            startActivity(intent)
        }
    }

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(categoryFragmentToolbar)
        (activity as SuperActivity).title = context!!.getString(R.string.title_activity_category)
        setRecipeData()
        val adapter = CategoryRecipeAdapter(recipes,context, openRecipeListener)
        recipeRecycler.adapter = adapter

    }

    override fun setAttr() {
        setLayout(R.layout.f_category)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun setRecipeData() {
        recipes.add(Recipe("Пицца", "Для большой компании", "2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"))
        recipes.add(Recipe("Борщ", "Хватит на всю семью", "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня"))
    }
}

