package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CategoryListAdapter
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: SuperFragment() {

    private var recipesListAdapter: CategoryListAdapter? = null
    private var categoryList: List<Category> = listOf()

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

    override fun initInterface(view: View?) {

        (activity as SuperActivity).setSupportActionBar(mainFragmentToolbar)
        (activity as SuperActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as SuperActivity).title = getString(R.string.title_category)

        setHasOptionsMenu(true)
        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_list_swipe_right)

        recipesListAdapter = CategoryListAdapter(categoryList, changeListener)
        mainFragmentRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mainFragmentRecycler.layoutAnimation = animation
        mainFragmentRecycler.adapter = recipesListAdapter
        mainFragmentRecycler.visibility = View.GONE

        getCategoriesFromServer()

    }

    override fun setAttr() {
        setLayout(R.layout.fragment_main)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun getCategoriesFromServer() {
        ApiService.getApi()
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                        if (response.isSuccessful) {
                            categoryList = response.body() ?: arrayListOf()
                        }
                        else{
                            fillListWithStub()
                        }
                        recipesListAdapter!!.onUpdateList(categoryList)
                        showList()
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        fillListWithStub()
                        recipesListAdapter!!.onUpdateList(categoryList)
                        showList()
                    }
                })
    }

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
    }

    private fun showProfileScreen(){
        val dialog = ProfilePageFragment()
        val fm = activity?.supportFragmentManager

        fm?.let { dialog.show(it,"profileDialog") }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile -> {
                showProfileScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillListWithStub(){
        categoryList = arrayListOf(
                Category("Первое"),
                Category("Второе"),
                Category("Салаты"),
                Category("Выпечка"),
                Category("Закуски"),
                Category("Напитки"),
                Category("Десерты")
        )
    }
}