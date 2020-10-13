package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.view.View
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.view.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment: SuperFragment() {
    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(favouritesFragmentToolbar)
        favouritesFragmentToolbar.title = getString(R.string.title_favourites)
    }

    override fun setAttr() {
        setLayout(R.layout.fragment_favourites)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}