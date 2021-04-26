package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.api.ApiService
import com.api.ApiService.ACCESS_TOKEN_KEY
import com.api.model.LoginRequest
import com.api.model.LoginResponse
import com.api.model.User
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_AUTH
import com.cookMeGood.makeItTasteIt.utils.ConstantContainer.INTENT_USER
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goShortToast
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : SuperActivity() {

    override fun setAttr() = setLayout(R.layout.activity_auth)

    override fun initInterface() {

        showLoggingForm(intent.extras!!.getBoolean(INTENT_AUTH))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                    ContextCompat.getColor(applicationContext, R.color.colorBlack)
        }

        authActivityLoginButton.setOnClickListener {
            val login = authActivityLoginEmail.text.toString()
            val password = authActivityLoginPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                goShortToast(applicationContext, "Заполните все поля")
            } else {
                loginUserOnServer(login, password)
            }
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
                registerUserOnServer(HelpUtils.getStubUser())
            }
        }
    }

    private fun registerUserOnServer(user: User) {
        ApiService.getApi(applicationContext)
                .addUser(user)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        when (response.code()) {
                            200 -> {
                                intent.putExtra(INTENT_USER, response.body())
                                startActivity(Intent(
                                        this@AuthActivity,
                                        MainActivity::class.java))
                            }
                            else -> {
                                goShortToast(applicationContext, "Ошибка!")
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        goShortToast(applicationContext, "Такой пользователь уже существует")
                    }
                })
    }

    private fun loginUserOnServer(login: String, password: String) {
        ApiService.getApi(applicationContext)
                .authorize(LoginRequest(login, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                            call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        when (response.code()) {
                            200 -> {
                                val sharedPreferences = applicationContext.getSharedPreferences(
                                        ApiService.PREF_NAME, Context.MODE_PRIVATE
                                )
                                sharedPreferences
                                        .edit()
                                        .putString(ACCESS_TOKEN_KEY, response.body()!!.jwtToken)
                                        .apply()
                                startActivity(Intent(
                                        this@AuthActivity,
                                        MainActivity::class.java)
                                )
                            }
                            500 -> {
                                goShortToast(applicationContext, "Ошибка!")
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        goShortToast(applicationContext, t.message.toString())
                    }
                })
    }

    private fun showLoggingForm(isLoggingIn: Boolean) {
        if (isLoggingIn) {
            authRegisterLayout.visibility = View.GONE
            authLoginLayout.visibility = View.VISIBLE
            authActivityLoginForgotPassword.visibility = View.VISIBLE
        } else {
            authRegisterLayout.visibility = View.VISIBLE
            authLoginLayout.visibility = View.GONE
            authActivityLoginForgotPassword.visibility = View.GONE
        }
    }
}