package com.cookMeGood.makeItTasteIt.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.cookMeGood.makeItTasteIt.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*


class SplashActivity : SuperActivity() {


    override fun initInterface() {

    }

    override fun setAttr() {
        setLayout(R.layout.activity_splash)
    }

    private val connectivityCheck = CoroutineScope(Dispatchers.Main) // Корутина, переназови если нужно

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var res : Boolean
        connectivityCheck.launch {

            val call  = async { getData()} // 2я корутина в которой мы посылаем запрос

            try {
                res = call.await() // ожидаем выполнения второй корутины

                if (res)
                Toast.makeText(this@SplashActivity, "Task  Done", Toast.LENGTH_SHORT).show()
                else (finish())

            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(this@SplashActivity, "Task not Done", Toast.LENGTH_SHORT).show()
                delay(3000)
            }
            startNextActivity()

        }
    }
    private fun startNextActivity(){
        intent = Intent(this@SplashActivity, AuthActivity::class.java)
        window.exitTransition = null;
        val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                splashLogo, "logoTransition")
        startActivity(intent, options.toBundle())
        supportFinishAfterTransition()
    }


    private suspend fun getData(): Boolean {
        delay(5000) //выполняем какой то запрос
        return true
    }
}

