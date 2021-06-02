package com.cookMeGood.makeItTasteIt.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.ContextCompat
import com.api.ApiService
import com.api.ApiService.ACCESS_TOKEN_KEY
import com.api.dto.request.LoginRequest
import com.api.dto.request.UserRegistrationRequest
import com.api.dto.response.UserResponse
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_AUTH
import com.cookMeGood.makeItTasteIt.utils.ContextUtils.goShortToast
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : SuperActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun setAttr() = setLayout(R.layout.activity_auth)

    override fun initInterface() {

        sharedPreferences = applicationContext.getSharedPreferences(
            ApiService.PREF_NAME, Context.MODE_PRIVATE
        )

        showLoggingForm(intent.extras!!.getBoolean(INTENT_AUTH))

        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

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

            if (name.isEmpty() ||
                username.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()
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
        ApiService.getApi().addUser(registrationRequest)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            sharedPreferences
                                .edit()
                                .putString(ACCESS_TOKEN_KEY, response.body()!!.jwtToken)
                                .apply()

                            DataContainer.currentUser = response.body()
                            ApiService.jwtToken = DataContainer.currentUser!!.jwtToken

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

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    goShortToast(applicationContext, "Ошибка соединения с сервером")
                }
            })
    }

    private fun loginUserOnServer(login: String, password: String) {
        ApiService.getApi().authorize(LoginRequest(login, password))
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            sharedPreferences
                                .edit()
                                .putString(ACCESS_TOKEN_KEY, response.body()!!.jwtToken)
                                .apply()

                            DataContainer.currentUser = response.body()!!
                            ApiService.jwtToken = DataContainer.currentUser!!.jwtToken

                            startActivity(
                                Intent(
                                    this@AuthActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        401 -> {
                            goShortToast(applicationContext, "Unauthorized!")
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
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
