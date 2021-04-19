package com.cookMeGood.makeItTasteIt.view.fragment

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
import com.miti.api.ApiService
import com.miti.api.model.Category
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: SuperFragment() {

    private var recipesListAdapter: CategoryListAdapter? = null
    private var categoryList: List<Category> = listOf()

    private lateinit var animation: LayoutAnimationController

    private var changeListener = object: OnFragmentChangeListener{
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
        getAllCategoriesFromServer()
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
    }

    private fun getAllCategoriesFromServer() {
        ApiService.getApi(requireContext())
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                        if (response.isSuccessful) {
                            categoryList = response.body() ?: emptyList()

                            if (recipesListAdapter == null) {
                                recipesListAdapter = CategoryListAdapter(categoryList, changeListener)
                                mainFragmentRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                                mainFragmentRecycler.layoutAnimation = animation
                                mainFragmentRecycler.adapter = recipesListAdapter
                            }
                            else {
                                recipesListAdapter!!.onUpdateList(categoryList)
                            }
                            showList()
                        }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}