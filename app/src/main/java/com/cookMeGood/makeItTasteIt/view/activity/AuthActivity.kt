package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.LogInDialogAdapter
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.api.RuntimeStorage.prefName
import com.cookMeGood.makeItTasteIt.api.RuntimeStorage.privateMode
import com.cookMeGood.makeItTasteIt.api.model.LoginRequest
import com.cookMeGood.makeItTasteIt.api.model.LoginResponse
import com.cookMeGood.makeItTasteIt.dto.User
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goToast
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity  : SuperActivity(), LogInDialogAdapter.LoginDialogListener {

    private var user: User = User()

    override fun initInterface() {
        window.enterTransition = null
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

        btn_signIn.setOnClickListener { clickSignIn() }
        btn_signUp.setOnClickListener { clickSignUp() }
        textAsGuest.setOnClickListener { asGuest() }
    }

    override fun setAttr() {
        setLayout(R.layout.activity_auth)
    }

    private fun clickSignIn() {
        val loginDialog = LogInDialogAdapter()
        loginDialog.show(supportFragmentManager, "Login dialog")
    }

    private fun clickSignUp() {
        val intent = Intent(this, RegActivity::class.java)
        startActivity(intent)
    }

    private fun asGuest() {
        val intent = Intent(this, StartActivity::class.java)
        user = User()
        startActivity(intent)
        finish()
    }

    override fun loginUser(login: String, password: String) {
        if (login == "" || password == "") {
            goToast(applicationContext, "Пустое поле!")
        } else {
            doAuth(login, password)
        }
    }

    private fun doAuth(login: String, password: String) {
        ApiService.getApi()
                .authorize(LoginRequest(login, password))
                .enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful){
                            when (response.code()) {
                                200 -> {
                                    getSharedPreferences(prefName, privateMode).edit().putString("access_token", response.body()!!.jwtToken).apply()

                                    val intent = Intent(applicationContext, StartActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                        else{
                            goToast(applicationContext, "Неправильный логин или пароль")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        goToast(applicationContext, "Ошибка соединения с сервером")
                    }
                })
    }
}



