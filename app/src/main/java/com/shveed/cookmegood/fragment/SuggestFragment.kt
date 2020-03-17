package com.shveed.cookmegood.fragment

import android.content.Intent
import android.view.View
import com.shveed.cookmegood.R
import com.shveed.cookmegood.activity.SuperActivity
import kotlinx.android.synthetic.main.f_suggest.*

class SuggestFragment: SuperFragment() {
    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(suggestFragmentToolbar)
        suggestFragmentToolbar.title = getString(R.string.title_suggest)
    }

    override fun setAttr() {
        setLayout(R.layout.f_suggest)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}