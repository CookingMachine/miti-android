package com.shveed.cookmegood.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shveed.cookmegood.data.dto.User
import com.shveed.cookmegood.R
import kotlinx.android.synthetic.main.activity_reg.*

class RegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
    btn_register.setOnClickListener { clickRegister() }
    }
    private fun checkEmptyInput(line:String):Boolean{ return line == "" }

    fun toAuth(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun clickRegister() {
        val name = edit_name.text.toString()
        val email = edit_Email.text.toString()
        val password = edit_password.text.toString()
        val output = "Заполните все поля"

        if (checkEmptyInput(name) ||
                checkEmptyInput(email) ||
                checkEmptyInput(password)) {
            val myToast = Toast.makeText(applicationContext,
                    output, Toast.LENGTH_SHORT)
            myToast.show()
        } else {
            val user: User? = null
            val intent = Intent(this@RegActivity, StartActivity::class.java)
            //intent.putExtra("userObject", user);
            startActivity(intent)
        }
    }
}

