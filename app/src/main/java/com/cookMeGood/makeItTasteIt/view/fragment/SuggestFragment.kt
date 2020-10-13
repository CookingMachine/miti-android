package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.view.activity.SuggestActivity
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import com.cookMeGood.makeItTasteIt.adapter.recyclerview.SuggestedRecipeListAdapter
import com.cookMeGood.makeItTasteIt.dto.Recipe
import kotlinx.android.synthetic.main.fragment_suggest.*

class SuggestFragment: SuperFragment() {

    private var suggestedRecipesList: List<Recipe> = listOf()
    private var suggestedRecipeListAdapter: SuggestedRecipeListAdapter? = null

    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(suggestFragmentToolbar)
        suggestFragmentToolbar.title = getString(R.string.title_suggest)

        getSuggestedRecipesByUserIdFromServer()
        showContent()

        suggestAddButton.setOnClickListener {
            val intent = Intent(context, SuggestActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setAttr() {
        setLayout(R.layout.fragment_suggest)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun getSuggestedRecipesByUserIdFromServer(){
        suggestFragmentProgressBar.visibility = View.GONE
    }

    private fun showContent(){
        if(suggestedRecipesList.isEmpty()){
            suggestEmptyListLayout.visibility = View.VISIBLE
            suggestFragmentList.visibility = View.GONE
        }
        else{
            suggestEmptyListLayout.visibility = View.GONE
            suggestFragmentList.visibility = View.VISIBLE
        }
    }
}