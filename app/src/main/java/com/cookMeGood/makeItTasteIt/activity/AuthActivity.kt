package com.cookMeGood.makeItTasteIt.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import com.api.ApiService
import com.api.ApiService.ACCESS_TOKEN_KEY
import com.api.dto.request.LoginRequest
import com.api.dto.request.UserRegistrationRequest
import com.api.dto.response.UserRegistrationResponse
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_AUTH
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_USER
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goShortToast
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
                || password.isEmpty()
            ) {
                goShortToast(applicationContext, "Заполните все поля")
            } else {
                registerUserOnServer(
                    UserRegistrationRequest(
                        username, password, email, name
                    )
                )
            }
        }
    }

    private fun registerUserOnServer(registrationRequest: UserRegistrationRequest) {
        ApiService.getApi(applicationContext)
            .addUser(registrationRequest)
            .enqueue(object : Callback<UserRegistrationResponse> {
                override fun onResponse(
                    call: Call<UserRegistrationResponse>,
                    response: Response<UserRegistrationResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            val sharedPreferences = applicationContext.getSharedPreferences(
                                ApiService.PREF_NAME, Context.MODE_PRIVATE
                            )
                            sharedPreferences
                                .edit()
                                .putString(ACCESS_TOKEN_KEY, response.body()!!.jwtToken)
                                .apply()
                            intent.putExtra(INTENT_USER, response.body())
                            startActivity(
                                Intent(
                                    this@AuthActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        else -> {
                            goShortToast(
                                applicationContext,
                                "Такой пользователь уже существует!"
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<UserRegistrationResponse>, t: Throwable) {
                    goShortToast(applicationContext, "Ошибка соединения с сервером")
                }
            })
    }

    private fun loginUserOnServer(login: String, password: String) {
        ApiService.getApi(applicationContext)
            .authorize(LoginRequest(login, password))
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>, response: Response<String>
                ) {
                    when (response.code()) {
                        200 -> {
                            val sharedPreferences = applicationContext.getSharedPreferences(
                                ApiService.PREF_NAME, Context.MODE_PRIVATE
                            )
                            sharedPreferences
                                .edit()
                                .putString(ACCESS_TOKEN_KEY, response.body())
                                .apply()
                            startActivity(
                                Intent(
                                    this@AuthActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        500 -> {
                            goShortToast(applicationContext, "Ошибка!")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    goShortToast(applicationContext, t.localizedMessage!!.toString())
                    throw t
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