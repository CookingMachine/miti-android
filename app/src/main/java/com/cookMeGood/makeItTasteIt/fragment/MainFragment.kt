package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CategoryListAdapter
import com.api.ApiService
import com.api.dto.Category
import com.api.dto.MainContent
import com.api.dto.Recipe
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_FAST_AND_DELICIOUS
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_LOW_CALORIES
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_RECIPE
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.getStubCategoryList
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.getStubRecipe
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : SuperFragment() {

    private var recipesListAdapter: CategoryListAdapter? = null
    private var dishOfTheDayRecipe: Recipe? = null

    private lateinit var animation: LayoutAnimationController

    private var changeListener = object : OnFragmentChangeListener {
        override fun replaceFragment(fragment: Fragment, category: Category) {

            val bundle = Bundle()
            bundle.putSerializable(INTENT_CATEGORY, category)
            fragment.arguments = bundle

            val listener = parentFragmentManager.beginTransaction()
            listener.replace(R.id.nav_host, fragment)
            listener.commit()
        }
    }

    override fun setAttr() = setLayout(R.layout.fragment_main)

    override fun initInterface(view: View?) {
        (activity as SuperActivity).title = getString(R.string.title_category)

        animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_swipe_right)
        mainFragmentRecycler.visibility = View.GONE

        setHasOptionsMenu(true)

//        getMainPageContentFromServer()
        DataContainer.mainContent = MainContent(getStubRecipe(),
                listOf(Category("ddd", "fff")),
                listOf(getStubRecipe()),
                listOf(getStubRecipe()))
        recipesListAdapter = CategoryListAdapter(
                DataContainer.mainContent!!.categoryList!!,
                changeListener
        )
        mainFragmentRecycler.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        mainFragmentRecycler.layoutAnimation = animation
        mainFragmentRecycler.adapter = recipesListAdapter
        showList()

        dishOfTheDayCard.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(INTENT_RECIPE, dishOfTheDayRecipe)
            startActivity(intent)
        }

        noCaloriesCard.setOnClickListener {
            changeListener.replaceFragment(
                    CategoryFragment(),
                    Category(INTENT_CATEGORY_LOW_CALORIES, "Мало калорий")
            )
        }

        fastAndDelicious.setOnClickListener {
            changeListener.replaceFragment(
                    CategoryFragment(),
                    Category(INTENT_CATEGORY_FAST_AND_DELICIOUS, "Быстро и вкусно")
            )
        }
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
    }

    private fun getMainPageContentFromServer() {
        ApiService.getApi(requireContext())
                .getMainPageContent()
                .enqueue(object : Callback<MainContent> {
                    override fun onResponse(call: Call<MainContent>,
                                            response: Response<MainContent>) {
                        if (response.isSuccessful) {
                            DataContainer.mainContent = response.body()
                            dishOfTheDayCard.title = DataContainer.mainContent!!.recipeOfTheDay!!.name!!

                            if (recipesListAdapter == null) {
                                recipesListAdapter = CategoryListAdapter(
                                        DataContainer.mainContent!!.categoryList!!,
                                        changeListener
                                )
                                mainFragmentRecycler.layoutManager = LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                )
                                mainFragmentRecycler.layoutAnimation = animation
                                mainFragmentRecycler.adapter = recipesListAdapter
                            } else {
                                recipesListAdapter!!
                                        .onUpdateList(DataContainer.mainContent!!.categoryList!!)
                            }
                            showList()
                        }
                    }

                    override fun onFailure(call: Call<MainContent>, t: Throwable) {
                        ContextUtils.goLongToast(requireContext(), t.message.toString())
                    }
                })
    }
}