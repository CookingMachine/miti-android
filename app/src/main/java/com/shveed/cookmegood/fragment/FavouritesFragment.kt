package com.shveed.cookmegood.fragment

import android.content.Intent
import android.view.View
import com.shveed.cookmegood.R
import com.shveed.cookmegood.activity.SuperActivity
import kotlinx.android.synthetic.main.f_favourites.*

class FavouritesFragment: SuperFragment() {
    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(favouritesFragmentToolbar)
        favouritesFragmentToolbar.title = getString(R.string.title_favourites)
    }

    override fun setAttr() {
        setLayout(R.layout.f_favourites)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}