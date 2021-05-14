package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_AUTH
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_USER
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: SuperActivity() {

    override fun setAttr() = setLayout(R.layout.activity_start)

    override fun initInterface() {
        window.enterTransition = null
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

        startActivitySignInButton.setOnClickListener {
            intent = Intent(this, AuthActivity::class.java)
            intent.putExtra(INTENT_AUTH, true)
            startActivity(intent)
            finish()
        }

        startActivitySignUpButton.setOnClickListener {
            intent = Intent(this, AuthActivity::class.java)
            intent.putExtra(INTENT_AUTH, false)
            startActivity(intent)
            finish()
        }

        startActivityAsGuest.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra(INTENT_USER, ContextUtils.getStubUser())
            startActivity(intent)
            finish()
        }
    }

}




