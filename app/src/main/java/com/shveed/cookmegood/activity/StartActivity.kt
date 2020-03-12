package com.shveed.cookmegood.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shveed.cookmegood.R
import com.shveed.cookmegood.fragment.*
import com.shveed.cookmegood.listener.FragmentChangeListener
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity: FragmentActivity(), FragmentChangeListener  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        nav_view_start.setOnNavigationItemSelectedListener(mOnNavigationItemSelected)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentStartFrameLayout, MainFragment())
                .commit()
     //   val currentUser = intent.getSerializableExtra("userObject") as User
    }
    private val mOnNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {menuItem -> when
        (menuItem.itemId){
        R.id.navigation_suggest ->{
            val fragment = SuggestFragment()
            replaceFragment(fragment)
            Log.d("tag","sjd")
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_favourites ->{
            val fragment = FavouritesFragment()
            replaceFragment(fragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_cart ->{
            val fragment = CartFragment()
            replaceFragment(fragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_profile ->{
            val fragment = CabinetFragment()
            replaceFragment(fragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_recipe ->{
            val fragment = MainFragment()
            replaceFragment(fragment)
            return@OnNavigationItemSelectedListener true
        }
    };false
    }

    override fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentStartFrameLayout, fragment, fragment.toString())
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
    }

    fun toSearch(view: View?) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    fun toCategories(view: View?) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentStartFrameLayout, MainFragment())
                .commit()
    }

}