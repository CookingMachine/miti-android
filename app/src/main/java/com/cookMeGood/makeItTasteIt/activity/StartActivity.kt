package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.fragment.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: SuperActivity()  {

    companion object{
        private const val FRAGMENT_MAIN = 0
        private const val FRAGMENT_SUGGEST = 1
        private const val FRAGMENT_FAVOURITES = 2
        private const val FRAGMENT_CART = 3
    }

    private val fragments = arrayListOf(
            MainFragment(),
            SuggestFragment(),
            FavouritesFragment(),
            CartFragment()
    )

    override fun initInterface() {
        nav_view_start.selectedItemId = R.id.navigation_main
        nav_view_start.setOnNavigationItemSelectedListener {

            var currentFragment: Int? = null

            when (it.itemId) {
                R.id.navigation_main -> currentFragment = FRAGMENT_MAIN
                R.id.navigation_suggest -> currentFragment = FRAGMENT_SUGGEST
                R.id.navigation_favourites -> currentFragment = FRAGMENT_FAVOURITES
                R.id.navigation_cart -> currentFragment = FRAGMENT_CART
            }

            if (currentFragment != null) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentStartFrameLayout, fragments[currentFragment])
                fragmentTransaction.commit()
            }

            true

        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentStartFrameLayout, fragments[FRAGMENT_MAIN])
        fragmentTransaction.commit()

        //   val currentUser = intent.getSerializableExtra("userObject") as User

    }

    override fun setAttr() {
        setLayout(R.layout.activity_start)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        for (item in fragments) {
            item.onResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {}
}