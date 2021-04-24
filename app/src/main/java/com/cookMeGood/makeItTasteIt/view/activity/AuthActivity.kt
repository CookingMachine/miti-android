package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goShortToast
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_AUTH
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.miti.api.model.User
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : SuperActivity() {

    override fun initInterface() {

        val isLoggingIn = intent.extras!!.getBoolean(INTENT_AUTH)

        if (isLoggingIn) {
            authRegisterLayout.visibility = View.GONE
        }
        else {
            authLoginLayout.visibility = View.GONE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)
        }

        authActivityRegisterButton.setOnClickListener {
            val name = authActivityRegisterName.text.toString()
            val username = authActivityRegisterLogin.text.toString()
            val email = authActivityRegisterEmail.text.toString()
            val password = authActivityRegisterPassword.text.toString()

            if (name.isEmpty()
                    || username.isEmpty()
                    || email.isEmpty()
                    || password.isEmpty()) {

                goShortToast(applicationContext, "Заполните все поля")

            } else {
                HelpUtils.getStubUser()
            }
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_auth)

    fun toAuth() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    private fun addUserToServer(user: User){
        com.miti.api.ApiService.getApi(applicationContext)
                .addUser(user)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@AuthActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        goShortToast(applicationContext, "Такой пользователь уже существует")
                    }
                })
    }
}