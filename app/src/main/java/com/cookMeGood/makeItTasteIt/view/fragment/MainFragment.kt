package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnFragmentChangeListener
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.CategoryListAdapter
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_CATEGORY
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: SuperFragment() {

    private var recipesListAdapter: CategoryListAdapter? = null
    private var categoryList: List<Category> = listOf()

    var changeListener = object: OnFragmentChangeListener{
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

        setHasOptionsMenu(true)
        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.anim_layout_list_swipe_right)

//        categoryList = ((activity as SuperActivity)
//                .intent
//                .getSerializableExtra(INTENT_MAIN_CONTENT) as MainContent)
//                .categoryList
        fillListWithStub()

        recipesListAdapter = CategoryListAdapter(categoryList, changeListener)
        mainFragmentRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mainFragmentRecycler.layoutAnimation = animation
        mainFragmentRecycler.adapter = recipesListAdapter
        mainFragmentRecycler.visibility = View.GONE

        if (categoryList.isNullOrEmpty()){
            fillListWithStub()
        }
        else {
            recipesListAdapter!!.onUpdateList(categoryList)
        }
        showList()
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun showList() {
        mainFragmentProgressBar.visibility = View.GONE
        mainFragmentRecycler.visibility = View.VISIBLE
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