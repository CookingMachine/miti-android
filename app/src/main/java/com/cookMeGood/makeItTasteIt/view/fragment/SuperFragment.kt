package com.cookMeGood.makeItTasteIt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class SuperFragment : Fragment() {
    private var layout = 0

    abstract fun initInterface(view: View?)
    abstract fun setAttr()
    abstract fun onResult(requestCode: Int, resultCode: Int, data: Intent?)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setAttr()
        return inflater.inflate(layout, null)
    }

    fun setLayout(layout: Int) {
        this.layout = layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInterface(view)
    }
}