package com.cookMeGood.makeItTasteIt.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.api.ApiService
import com.api.ApiService.ACCESS_TOKEN_KEY
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.listener.OnUserSignOutListener
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.fragment.ProfilePageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SuperActivity() {

    private lateinit var navController: NavController

    private val onUserSignOutListener = object : OnUserSignOutListener {
        override fun signOut(context: Context) {
            val sharedPreferences =
                context.getSharedPreferences(ApiService.PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences
                .edit()
                .remove(ACCESS_TOKEN_KEY)
                .apply()
            DataContainer.currentUser = null
            ApiService.jwtToken = null
            intent = Intent(context, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_main)

    override fun initInterface() {
        setSupportActionBar(mainActivityToolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        navController = findNavController(R.id.nav_host)
        mainNavView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showProfileScreen() {
        val dialog = ProfilePageFragment(onUserSignOutListener)
        supportFragmentManager.let { dialog.show(it, "profileDialog") }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
