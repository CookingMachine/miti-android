package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.RecipeListAdapter
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.dto.Recipe
import com.cookMeGood.makeItTasteIt.api.RecipeApiService
import com.cookMeGood.makeItTasteIt.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.utils.IntentContainer
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_RECIPE
import kotlinx.android.synthetic.main.fragment_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment: SuperFragment() {

    private var recipesList = listOf<Recipe>()
    private var recipesListListAdapter: RecipeListAdapter? = null

    private val openRecipeListener = object: OnOpenRecipeListener{
        override fun openRecipe(recipe: Recipe) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(INTENT_RECIPE, recipe)
            startActivity(intent)
        }
    }

    override fun setAttr() {
        setLayout(R.layout.fragment_category)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(categoryFragmentToolbar)
        (activity as SuperActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as SuperActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as SuperActivity).title = requireContext().getString(R.string.title_activity_category)

        val category = requireArguments().getSerializable(IntentContainer.INTENT_CATEGORY) as Category

        recipesListListAdapter = RecipeListAdapter(recipesList,context, openRecipeListener)
        categoryFragmentRecipeList.adapter = recipesListListAdapter

        getRecipesByCategoryIdFromServer(category.id!!)
    }

    private fun setRecipeData() {
        recipesList = arrayListOf(
                Recipe(null,"Пицца", "Для большой компании", null, null, "2:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"),
                Recipe(null,"Борщ", "Хватит на всю семью", null, null, "4:00", java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня")
        )
    }

    private fun getRecipesByCategoryIdFromServer(categoryId: String) {

        RecipeApiService.getApi()
                .getRecipesByCategoryId(categoryId)
                .enqueue(object : Callback<List<Recipe>> {
                    override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                        recipesList = response.body() ?: arrayListOf()
                        recipesListListAdapter!!.onUpdateList(recipesList)
                        showList()
                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        setRecipeData()
                        Toast.makeText(context, "Ошибка соединения с сервером", Toast.LENGTH_LONG).show()
                        recipesListListAdapter!!.onUpdateList(recipesList)
                        showList()
                    }
                })
    }

    private fun showList(){
        categoryFragmentProgressBar.visibility = View.GONE
        categoryFragmentRecipeList.visibility = View.VISIBLE
    }
}

