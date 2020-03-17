package com.shveed.cookmegood.fragment

import android.content.Intent
import android.view.View
import com.shveed.cookmegood.R
import com.shveed.cookmegood.activity.SuperActivity
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