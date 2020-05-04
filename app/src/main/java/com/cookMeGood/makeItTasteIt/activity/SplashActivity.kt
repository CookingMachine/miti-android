package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.cookMeGood.makeItTasteIt.R


class SplashActivity : SuperActivity() {

    companion object {
        private const val SPLASH_TIME_OUT = 3000L
    }

    override fun initInterface() {
    }

    override fun setAttr() {
        setLayout(R.layout.activity_splash)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,AuthActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}

