package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import kotlinx.android.synthetic.main.fragment_suggest.*

class SuggestFragment: SuperFragment() {
    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(suggestFragmentToolbar)
        suggestFragmentToolbar.title = getString(R.string.title_suggest)
    }

    override fun setAttr() {
        setLayout(R.layout.fragment_suggest)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}