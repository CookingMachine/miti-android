package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.LogInDialogAdapter
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.api.model.LoginRequest
import com.cookMeGood.makeItTasteIt.api.model.LoginResponse
import com.cookMeGood.makeItTasteIt.api.dto.User
import com.cookMeGood.makeItTasteIt.utils.ApplicationContext
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goToast
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity: SuperActivity(), LogInDialogAdapter.LoginDialogListener {

    override fun setAttr() = setLayout(R.layout.activity_auth)

    override fun initInterface() {
        ApplicationContext.setContext(this)

        window.enterTransition = null
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

        btn_signIn.setOnClickListener {
            val loginDialog = LogInDialogAdapter()
            loginDialog.show(supportFragmentManager, "Login dialog")
        }

        btn_signUp.setOnClickListener {
            intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }

        textAsGuest.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", User(null, "guest", null, null))
            startActivity(intent)
            finish()
        }
    }

    override fun loginUser(login: String, password: String) {
        if (login == "" || password == "") {
            goToast(applicationContext, "Пустое поле!")
        } else {
            ApiService.getApi()
                    .authorize(LoginRequest(login, password))
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            when (response.code()) {
                                200 -> {
                                    getSharedPreferences(ApiService.prefName, Context.MODE_PRIVATE)
                                            .edit()
                                            .putString("access_token", response.body()!!.jwtToken)
                                            .apply()

                                    intent = Intent(applicationContext, SplashActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                401 -> {
                                    goToast(applicationContext, "Неправильный логин или пароль")
                                }
                                500 -> {
                                    goToast(applicationContext, "ОШИБКА СЕРВЕРА")
                                }
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            goToast(applicationContext, "Ошибка соединения с сервером")
                        }
                    })
        }
    }
}




