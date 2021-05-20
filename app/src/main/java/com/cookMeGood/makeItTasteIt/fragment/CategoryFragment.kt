package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.ApiService
import com.api.dto.Category
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.activity.SuggestActivity
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeListAdapter
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_FAST_AND_DELICIOUS
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_LOW_CALORIES
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_RECIPE
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goLongToast
import kotlinx.android.synthetic.main.fragment_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : SuperFragment() {

    private var recipeList = listOf<Recipe>()
    private var recipeListAdapter: RecipeListAdapter? = null

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
        val category = requireArguments().getSerializable(INTENT_CATEGORY) as Category
        (activity as SuperActivity).title = category.name

        categoryFragmentProgressBar.visibility = View.VISIBLE

        when (category.id) {
            INTENT_CATEGORY_LOW_CALORIES -> {
                recipeList = DataContainer.mainContent!!.lowCalorieRecipes!!
            }
            INTENT_CATEGORY_FAST_AND_DELICIOUS -> {
                recipeList = DataContainer.mainContent!!.fastAndDeliciousRecipes!!
            }
            else -> {
                getRecipesByCategoryIdFromServer(category.id!!)
            }
        }
        if (recipeListAdapter == null) {
            initListAdapter()
        }
        showList()

        getRecipesByCategoryIdFromServer(category.id!!)

        floatingButton.setOnClickListener {
            startActivity(Intent(context, SuggestActivity::class.java))
        }
    }

    private fun getRecipesByCategoryIdFromServer(categoryId: String) {
        ApiService.getApi(requireContext())
                .getRecipesByCategoryId(categoryId)
                .enqueue(object : Callback<List<Recipe>> {
                    override fun onResponse(
                            call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                        if (response.isSuccessful) {
                            recipeList = response.body() ?: ContextUtils.getStubRecipeList()
                        }
                    }
                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        goLongToast(requireContext(), t.message.toString())
                    }
                })
    }

    private fun initListAdapter() {
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.shape_default_devider)!!
        )

        recipeListAdapter = RecipeListAdapter(recipeList, context, openRecipeListener)
        categoryFragmentRecipeList.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)
        categoryFragmentRecipeList.addItemDecoration(dividerItemDecoration)
        categoryFragmentRecipeList.adapter = recipeListAdapter
    }

    private fun showList() {
        categoryFragmentProgressBar.visibility = View.GONE
        categoryFragmentRecipeList.visibility = View.VISIBLE
    }

}

