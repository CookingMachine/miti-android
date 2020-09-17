package com.cookMeGood.makeItTasteIt.activity

import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.adapter.dialog.LogInDialogAdapter
import com.cookMeGood.makeItTasteIt.dto.User
import kotlinx.android.synthetic.main.activity_auth.*

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
            goToast("Пустое поле!")
        } else {
            doAuth(login, password)
        }
    }

    private fun goToast(output: String) {
        val errorToast = Toast.makeText(this, output, Toast.LENGTH_SHORT)
        errorToast.show()
    }

    private fun doAuth(login: String, pass: String) {
//        NetworkService().userApi.checkUser(login, pass)!!
//                .enqueue(object : Callback<User?> {
//                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
//
//                        val user = response.body()!!
//
//                        if (user == null) {
//                            goToast("Неправильный логин или пароль")
//                        } else {
//                            goToast("Добро пожаловать, $login")
//                            val intent = Intent(this@AuthActivity,
//                                    StartActivity::class.java)
//                            startActivity(intent)
//                        }
//                    }
//                    override fun onFailure(call: Call<User?>, t: Throwable) {
//                        goToast(t.localizedMessage!!)
//                    }
//                })
    }
}



