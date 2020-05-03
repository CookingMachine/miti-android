package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.cookMeGood.makeItTasteIt.R

private const val SPLASH_TIME_OUT:Long = 3000
class SplashActivity : SuperActivity() {
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

