package com.cookMeGood.makeItTasteIt.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.dto.MainContent
import com.cookMeGood.makeItTasteIt.utils.ApplicationContext
import com.cookMeGood.makeItTasteIt.utils.IntentContainer.INTENT_MAIN_CONTENT
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : SuperActivity() {

    private val waitForResponseCoroutine = CoroutineScope(Dispatchers.Main)
    private var isAuthenticated: Boolean = true
    private var mainContent: MainContent? = null

    override fun setAttr() = setLayout(R.layout.activity_splash)

    override fun initInterface() {

        ApplicationContext.setContext(applicationContext)

        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.colorBlack)

        waitForResponseCoroutine.launch {

            val call  = async { getData() }

            try {
                call.await()
            }
            catch (e:Exception) {
                e.printStackTrace()
                Toast.makeText(this@SplashActivity, "Server is unavailable\nTry again later", Toast.LENGTH_SHORT).show()
                delay(3000)
            }
            startNextActivity()
        }
    }

    private suspend fun getData(): Boolean {
        var timeLimit = 0

        while (mainContent == null && timeLimit < 10 && isAuthenticated){
            getCategoriesFromServer()
            delay(2000)
            timeLimit += 2
        }

        return true
    }

    private fun startNextActivity(){
        when (isAuthenticated){
            true -> {
                val bundle = Bundle()
                bundle.putSerializable(INTENT_MAIN_CONTENT, mainContent)

                intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtras(bundle)

                window.exitTransition = null
                startActivity(intent)
                supportFinishAfterTransition()
            }
            false -> {
                val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                        splashLogo, "logoTransition")

                intent = Intent(this@SplashActivity, AuthActivity::class.java)

                window.exitTransition = null
                startActivity(intent, options.toBundle())
                supportFinishAfterTransition()
            }
        }
    }

    private fun getCategoriesFromServer() {
        ApiService.getApi()
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                            when (response.code()) {
                                200 -> {
                                    mainContent = MainContent(response.body() ?: arrayListOf())
                                }
                                401 -> {
                                    isAuthenticated = false
                                }
                            }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
