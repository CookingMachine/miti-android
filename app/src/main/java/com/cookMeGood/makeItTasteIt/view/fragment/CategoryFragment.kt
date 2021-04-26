package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.ApiService
import com.api.model.Category
import com.api.model.Recipe
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnOpenRecipeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.RecipeListAdapter
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_RECIPE
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.view.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.view.activity.SuggestActivity
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
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
        (activity as SuperActivity).title = requireContext().getString(R.string.title_activity_category)
        (activity as SuperActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        categoryFragmentProgressBar.visibility = View.VISIBLE

        val category = requireArguments().getSerializable(INTENT_CATEGORY) as Category

        getRecipesByCategoryIdFromServer(category.id!!)

        floatingButton.setOnClickListener {
            startActivity(Intent(context, SuggestActivity::class.java))
        }
    }

    private fun getRecipesByCategoryIdFromServer(categoryId: String) {

        if (recipeListAdapter == null) {
            initListAdapter()
        }

        ApiService.getApi(requireContext())
                .getRecipesByCategoryId(categoryId)
                .enqueue(object : Callback<List<Recipe>> {
                    override fun onResponse(
                            call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                        if (response.isSuccessful) {
                            recipeList = response.body() ?: HelpUtils.getStubRecipeList()
                            recipeListAdapter!!.onUpdateList(recipeList)
                            showList()
                        }
                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        recipeList = HelpUtils.getStubRecipeList()
                        recipeListAdapter!!.onUpdateList(recipeList)
                        showList()
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

