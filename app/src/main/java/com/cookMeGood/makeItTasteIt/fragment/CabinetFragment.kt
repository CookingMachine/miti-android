package com.cookMeGood.makeItTasteIt.fragment

import android.content.Intent
import android.view.View
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.activity.SuperActivity
import kotlinx.android.synthetic.main.f_cabinet.*

class CabinetFragment: SuperFragment() {
    override fun initInterface(view: View?) {
        (activity as SuperActivity).setSupportActionBar(cabinetFragmentToolbar)
        (activity as SuperActivity).title = getString(R.string.title_profile)

    }

    override fun setAttr() {
        setLayout(R.layout.f_cabinet)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }
}