package com.cookMeGood.makeItTasteIt.adapter.listener

import androidx.fragment.app.Fragment
import com.cookMeGood.makeItTasteIt.dto.Category

interface OnFragmentChangeListener {

    fun replaceFragment(fragment: Fragment, category: Category)

}