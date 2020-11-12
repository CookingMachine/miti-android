package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: SuperActivity()  {

    override fun initInterface() {
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.primaryColor)

        val navController = findNavController(R.id.nav_host)
        nav_view_start.setupWithNavController(navController)

       // val currentUser = intent.getSerializableExtra("userObject") as User
    }

    override fun setAttr() {
        setLayout(R.layout.activity_start)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}