package com.cookMeGood.makeItTasteIt.view.activity

import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.dto.User
import com.cookMeGood.makeItTasteIt.utils.HelpUtils.goToast
import kotlinx.android.synthetic.main.activity_reg.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegActivity : SuperActivity() {

    override fun initInterface() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)
        }

        btn_register.setOnClickListener {
            onRegisterClicked()
        }
    }

    override fun setAttr() = setLayout(R.layout.activity_reg)

    fun toAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    private fun onRegisterClicked() {
        val name = edit_name.text.toString()
        val username = edit_username.text.toString()
        val email = edit_Email.text.toString()
        val password = edit_password.text.toString()

        if (name.isEmpty()
                || username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            goToast(applicationContext, "Заполните все поля")

        } else {
            addUserToServer(User(name, username, password, email, 0))
        }
    }

    private fun addUserToServer(user: User){
        ApiService.getApi()
                .addUser(user)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@RegActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        goToast(applicationContext, "Такой пользователь уже существует")
                    }
                })
    }
}