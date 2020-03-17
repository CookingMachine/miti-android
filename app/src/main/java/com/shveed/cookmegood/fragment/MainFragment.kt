package com.shveed.cookmegood.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.shveed.cookmegood.R
import com.shveed.cookmegood.adapter.RecipesGridAdapter
import com.shveed.cookmegood.data.NetworkService
import com.shveed.cookmegood.data.RuntimeStorage
import com.shveed.cookmegood.data.dto.Category
import com.shveed.cookmegood.listener.OnFragmentChangeListener
import kotlinx.android.synthetic.main.include_fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: SuperFragment() {

    private var recipesAdapter: RecipesGridAdapter? = null
    private var categoryList: List<String>? = listOf() //todo Поменять на List<Category> после сервера

    private var changeListener = object: OnFragmentChangeListener{
        override fun replaceFragment(fragment: Fragment) {
            val listener = fragmentManager!!.beginTransaction()
            listener.replace(R.id.fragmentStartFrameLayout, fragment)
            listener.commit()
        }
    }

    override fun initInterface(view: View?) {

        recipesAdapter = RecipesGridAdapter(context!!, categoryList!!, changeListener)
        mainFragmentRecycler.layoutManager = GridLayoutManager(context, 2)
        mainFragmentRecycler.adapter = recipesAdapter

        mainFragmentRecycler.visibility = View.GONE
        getCategoriesFromServer()

    }

    override fun setAttr() {
        setLayout(R.layout.f_main)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun getCategoriesFromServer() {
        NetworkService.getInstance()
                .categoryApi
                .allCategories
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                        showList()
                        for (category in response.body()!!) {
                            RuntimeStorage.newInstance().categories.add(category.name)
                        }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        showList()
                        Toast.makeText(context, "Нет связи с сервером", Toast.LENGTH_SHORT).show()
                        RuntimeStorage.newInstance().categories = listOf("Каши", "Салаты", "Супы", "Рыба и Мясо", "Выпечка", "Закуски", "Десерты", "Напитки", "Заготовки на зиму")
                        recipesAdapter!!.onUpdateList(RuntimeStorage.newInstance().categories)
                    }
                })
    }

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
    }
}