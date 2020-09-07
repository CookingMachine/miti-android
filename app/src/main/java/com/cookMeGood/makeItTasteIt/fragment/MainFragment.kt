package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.graphics.Point
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.CategoryAdapter
import com.cookMeGood.makeItTasteIt.data.NetworkService
import com.cookMeGood.makeItTasteIt.data.RuntimeStorage
import com.cookMeGood.makeItTasteIt.data.dto.Category
import com.cookMeGood.makeItTasteIt.listener.OnFragmentChangeListener
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: SuperFragment() {

    private var categoryAdapter: CategoryAdapter? = null
    private var categoryList: List<String>? = listOf() //todo Поменять на List<Category> после сервера
    private var changeListener = object: OnFragmentChangeListener{
        override fun replaceFragment(fragment: Fragment) {
            val listener = fragmentManager!!.beginTransaction()
            listener.replace(R.id.fragmentStartFrameLayout, fragment).addToBackStack("TAG")
            listener.commit()
        }
    }

    override fun initInterface(view: View?) {

        (activity as SuperActivity).setSupportActionBar(mainFragmentToolbar)
        (activity as SuperActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as SuperActivity).title = getString(R.string.title_category)

        setHasOptionsMenu(true)

        val display = requireActivity().windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenWidth = point.x
        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_list_swipe_right)
        categoryAdapter = CategoryAdapter(categoryList,changeListener)
        mainFragmentRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mainFragmentRecycler.layoutAnimation = animation
        mainFragmentRecycler.adapter = categoryAdapter
        mainFragmentRecycler.visibility = View.GONE
        getCategoriesFromServer()

    }

    override fun setAttr() {
        setLayout(R.layout.fragment_main)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun getCategoriesFromServer() {
        NetworkService()
                .categoryApi
                .allCategories
                ?.enqueue(object : Callback<List<Category?>?> {
                    override fun onResponse(call: Call<List<Category?>?>, response: Response<List<Category?>?>) {
                        showList()
                        for (category in response.body()!!) {
                            RuntimeStorage.newInstance()?.categories?.add(category?.name!!)
                        }
                    }

                    override fun onFailure(call: Call<List<Category?>?>, t: Throwable) {
                        showList()
                        Toast.makeText(context, "Нет связи с сервером", Toast.LENGTH_SHORT).show()
                        RuntimeStorage.newInstance()!!.categories = arrayListOf("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки", "Заготовки на зиму")
                        categoryAdapter!!.onUpdateList(RuntimeStorage.newInstance()!!.categories)
                    }
                })
    }

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
    }

    private fun showProfileScreen(){
        val dialog = ProfileDialogFragment()
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
}