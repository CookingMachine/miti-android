package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeListAdapter
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.api.dto.Category
import com.cookMeGood.makeItTasteIt.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_RECIPE
import com.cookMeGood.makeItTasteIt.view.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.view.activity.SuggestActivity
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment: SuperFragment() {

    private var recipesList = listOf<Recipe>()
    private var recipesListListAdapter: RecipeListAdapter? = null

    private val openRecipeListener = object : OnOpenRecipeListener {
        override fun openRecipe(recipe: Recipe) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(INTENT_RECIPE, recipe)
            startActivity(intent)
        }
    }

    override fun setAttr() = setLayout(R.layout.fragment_category)

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = requireContext().getString(R.string.title_activity_category)
        (activity as SuperActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val category = requireArguments().getSerializable(INTENT_CATEGORY) as Category

        recipesListListAdapter = RecipeListAdapter(recipesList, context, openRecipeListener)
        categoryFragmentRecipeList.adapter = recipesListListAdapter

        getRecipesByCategoryIdFromServer(category.id!!)

        floatingButton.setOnClickListener {
            startActivity(Intent(context, SuggestActivity::class.java))
        }
    }

    private fun getRecipesByCategoryIdFromServer(categoryId: String) {
        ApiService
                .getApi()
                .getRecipesByCategoryId(categoryId)
                .enqueue(object : Callback<List<Recipe>> {
                    override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
//                        recipesList = response.body() ?: arrayListOf()

                        setRecipeListStub()
                        recipesListListAdapter!!.onUpdateList(recipesList)
                        showList()
                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        Toast.makeText(context, "Ошибка соединения с сервером", Toast.LENGTH_LONG).show()
                        setRecipeListStub()
                        recipesListListAdapter!!.onUpdateList(recipesList)
                        showList()
                    }
                })
    }

    private fun showList() {
        categoryFragmentProgressBar.visibility = View.GONE
        categoryFragmentRecipeList.visibility = View.VISIBLE
    }

    private fun setRecipeListStub() {
        recipesList = arrayListOf(
                Recipe(null, "Пицца", "Важным условием настоящей пиццы bbq является ее запекание на углях или дровах. Для этого существуют специальные печи. Только тогда лепешка приобретет вкус и аромат дымка... ", "", null, Category(), java.lang.String.valueOf(R.drawable.image_recipe_background), "Итальянская кухня"),
                Recipe(null, "Борщ", "Хватит на всю семью", "", null, Category(), java.lang.String.valueOf(R.drawable.image_recipe_background), "Украинская кухня")
        )
    }
}

