package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.dto.User
import com.cookMeGood.makeItTasteIt.fragment.CartFragment
import com.cookMeGood.makeItTasteIt.fragment.FavouritesFragment
import com.cookMeGood.makeItTasteIt.fragment.MainFragment
import com.cookMeGood.makeItTasteIt.fragment.SuggestFragment
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: SuperActivity()  {

    override fun initInterface() {
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