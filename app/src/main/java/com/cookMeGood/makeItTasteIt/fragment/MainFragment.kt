package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.ApiService
import com.api.dto.Category
import com.api.dto.MainContent
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.RecipeActivity
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CategoryListAdapter
import com.cookMeGood.makeItTasteIt.container.AnimationContainer
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_FAST_AND_DELICIOUS
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_CATEGORY_LOW_CALORIES
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_RECIPE
import com.cookMeGood.makeItTasteIt.container.LogContainer.ERROR_MAIN_PAGE_RESPONSE
import com.cookMeGood.makeItTasteIt.container.MessageContainer.ERROR_SERVER_UNAVAILABLE
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goLongToast
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.shimmer_fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : SuperFragment() {

    private var recipesListAdapter: CategoryListAdapter? = null
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

        mainFragmentRecycler.visibility = View.GONE
        mainFragmentContent.visibility = View.GONE
        mainFragmentShimmer.visibility = View.VISIBLE

        setHasOptionsMenu(true)

        getMainPageContentFromServer()

        dishOfTheDayCard.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(INTENT_RECIPE, DataContainer.mainContent!!.recipeOfTheDay)
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
        mainFragmentShimmer.visibility = View.GONE
        mainFragmentContent.visibility = View.VISIBLE
        mainFragmentRecycler.visibility = View.VISIBLE
    }

    private fun getMainPageContentFromServer() {
        ApiService.getApi().getMainPageContent()
            .enqueue(object : Callback<MainContent> {
                override fun onResponse(
                    call: Call<MainContent>,
                    response: Response<MainContent>
                ) {
                    if (response.isSuccessful) {
                        DataContainer.mainContent = response.body()
                        dishOfTheDayCard.title = DataContainer.mainContent!!.recipeOfTheDay!!.name!!

                        initOrUpdateAdapter()
                        showList()
                    } else {
                        goLongToast(requireContext(), ERROR_SERVER_UNAVAILABLE)
                    }
                }

                override fun onFailure(call: Call<MainContent>, t: Throwable) {
                    goLongToast(requireContext(), t.message.toString())
                    Log.e(ERROR_MAIN_PAGE_RESPONSE, t.message.toString())
                }
            })
    }

    private fun initOrUpdateAdapter() {
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
            mainFragmentRecycler.layoutAnimation =
                AnimationContainer(requireContext()).ANIM_SWIPE_RIGHT
            mainFragmentRecycler.adapter = recipesListAdapter
        } else {
            recipesListAdapter!!
                .onUpdateList(DataContainer.mainContent!!.categoryList!!)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as SuperActivity).supportActionBar!!.elevation =
            ContextUtils.convertDpToPixel(4F, requireContext())
    }
}
