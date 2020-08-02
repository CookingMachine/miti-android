package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.activity_reg.*

class RegActivity : SuperActivity() {

    override fun initInterface() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)
        }

        btn_register.setOnClickListener {
            clickRegister()
        }
    }

    override fun setAttr() {
        setLayout(R.layout.activity_reg)
    }

    fun toAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
    private fun clickRegister() {
        val name = edit_name.text.toString()
        val email = edit_Email.text.toString()
        val password = edit_password.text.toString()
        val output = "Заполните все поля"

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

            HelpUtils.goToast(applicationContext, output)

        } else {

            //val user: User? = null
            val intent = Intent(this@RegActivity, StartActivity::class.java)
            //intent.putExtra("userObject", user);
            startActivity(intent)
        }
    }
}

