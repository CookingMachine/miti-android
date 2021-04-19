package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.view.fragment.ProfilePageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: SuperActivity()  {

    override fun setAttr() = setLayout(R.layout.activity_main)

    override fun initInterface() {
        setSupportActionBar(mainActivityToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =  SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val navController = findNavController(R.id.nav_host)
        mainNavView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun showProfileScreen(){
        val dialog = ProfilePageFragment()
        supportFragmentManager.let { dialog.show(it, "profileDialog") }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search -> {
                intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.action_profile -> {
                showProfileScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}