package com.cookMeGood.makeItTasteIt.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

abstract class SuperActivity : AppCompatActivity() {
    private var layout = 0

    abstract fun initInterface()
    abstract fun setAttr()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAttr()
        setContentView(layout)
        initInterface()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun setLayout(layout: Int) {
        this.layout = layout
    }


    private fun setStatusBarBackgroundResource(colorResource: Int) {
        window.statusBarColor = ContextCompat.getColor(applicationContext, colorResource)
    }

    private fun setStatusBarIconsDark(dark: Boolean) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return

        window.decorView.systemUiVisibility =
                if (dark)
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else
                    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}
