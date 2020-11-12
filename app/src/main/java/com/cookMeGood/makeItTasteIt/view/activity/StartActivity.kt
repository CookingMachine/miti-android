package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.view.fragment.ProfilePageFragment
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: SuperActivity()  {

    override fun setAttr() = setLayout(R.layout.activity_start)

    override fun initInterface() {
        setSupportActionBar(startActivityToolbar)
        startActivityToolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.primaryColor)

        val navController = findNavController(R.id.nav_host)
        nav_view_start.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.start_activity_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun showProfileScreen(){
        val dialog = ProfilePageFragment()
        supportFragmentManager.let { dialog.show(it,"profileDialog") }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_search -> {
                intent = Intent(this@StartActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.action_profile -> {
                showProfileScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}