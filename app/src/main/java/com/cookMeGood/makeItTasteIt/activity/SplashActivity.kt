package com.cookMeGood.makeItTasteIt.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.IntentContainer.INTENT_MAIN_CONTENT
import com.api.ApiService
import com.api.dto.Category
import com.api.dto.MainContent
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : SuperActivity() {

    private val waitForResponseCoroutine = CoroutineScope(Dispatchers.Main)
    private var isAuthenticated: Boolean = false

    override fun setAttr() = setLayout(R.layout.activity_splash)

    override fun initInterface() {

        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

        waitForResponseCoroutine.launch {
            val call  = async { getData() }

            try {
                call.await()
                delay(2000)
            }
            catch (e:Exception) {
                e.printStackTrace()
                Toast.makeText(this@SplashActivity,
                        "Server is unavailable\nTry again later",
                        Toast.LENGTH_SHORT
                ).show()
                delay(3000)
            }
            startNextActivity()
        }
    }

    private suspend fun getData(): Boolean {
        var timeLimit = 0

        while (timeLimit < 10 && isAuthenticated){
            delay(2000)
            timeLimit += 2
        }

        return true
    }

    private fun startNextActivity(){
        when (isAuthenticated) {
            true -> {
                intent = Intent(this@SplashActivity, MainActivity::class.java)

                window.exitTransition = null
                startActivity(intent)
            }
            false -> {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@SplashActivity,
                        splashLogo,
                        "logoTransition"
                )

                intent = Intent(this@SplashActivity, StartActivity::class.java)

                window.exitTransition = null
                startActivity(intent, options.toBundle())

            }
        }
        supportFinishAfterTransition()
    }
}
