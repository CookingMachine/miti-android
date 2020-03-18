package com.shveed.cookmegood.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shveed.cookmegood.R
import com.shveed.cookmegood.adapter.LogInDialogAdapter
import com.shveed.cookmegood.data.NetworkService
import com.shveed.cookmegood.data.dto.User
import kotlinx.android.synthetic.main.activity_authorization.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity  : AppCompatActivity(), LogInDialogAdapter.LoginDialogListener {
    var user: User = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        btn_signIn.setOnClickListener { clickSignIn() }
        btn_signUp.setOnClickListener { clickSignUp() }
        textAsGuest.setOnClickListener { asGuest() }
    }

    private fun clickSignIn() {
        val loginDialog = LogInDialogAdapter()
        loginDialog.show(supportFragmentManager, "Login dialog")
    }

    private fun clickSignUp() {
        val intent = Intent(this, RegActivity::class.java)
        startActivity(intent)
    }

    fun asGuest() {
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
        NetworkService()
                .userApi
                .checkUser(login, pass)
                ?.enqueue(object : Callback<User?> {
                    override fun onResponse(
                            call: Call<User?>,
                            response: Response<User?>) {
                        var user = response.body()!!
                        if (user == null) {
                            goToast("Неправильный логин или пароль")
                        } else {
                            goToast("Добро пожаловать, $login")
                            val intent = Intent(this@LoginActivity,
                                    StartActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    override fun onFailure(
                            call: Call<User?>,
                            t: Throwable) {
                        goToast(t.localizedMessage)
                    }
                })
    }
}



