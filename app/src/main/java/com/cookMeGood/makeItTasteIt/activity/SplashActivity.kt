package com.cookMeGood.makeItTasteIt.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : SuperActivity() {

    companion object {
        private const val SPLASH_TIME_OUT = 1500L
    }

    override fun initInterface() {

    }

    override fun setAttr() {
        setLayout(R.layout.activity_splash)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            intent = Intent(this, AuthActivity::class.java)
            window.exitTransition = null;
            val options = ActivityOptions.makeSceneTransitionAnimation(this,
                    splashLogo, "logoTransition")
            startActivity(intent, options.toBundle())
            supportFinishAfterTransition()
        }, SPLASH_TIME_OUT)
    }
}

