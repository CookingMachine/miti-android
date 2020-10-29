package com.cookMeGood.makeItTasteIt.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.widget.Toast
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.api.RuntimeStorage
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashActivity : SuperActivity() {

    private val waitForResponseCoroutine = CoroutineScope(Dispatchers.Main)
    private var isAuthenticated: Boolean = false
    private var mainContent = listOf<Category>()

    override fun setAttr() {
        setLayout(R.layout.activity_splash)
    }

    override fun initInterface() {

        RuntimeStorage.accessToken = getSharedPreferences(RuntimeStorage.prefName, RuntimeStorage.privateMode)
                .getString("access_token", "")

        var res : Boolean

        waitForResponseCoroutine.launch {

            val call  = async { getData() } // 2я корутина в которой мы посылаем запрос

            try {
                res = call.await()

                if (res) {
                    Toast.makeText(this@SplashActivity, "Task  Done", Toast.LENGTH_SHORT).show()
                }
                else {
                    finish()
                }

            } catch (e:Exception) {
                e.printStackTrace()
                Toast.makeText(this@SplashActivity, "Task not Done", Toast.LENGTH_SHORT).show()
                delay(3000)
            }
            startNextActivity()

        }
    }

    private suspend fun getData(): Boolean {
        var timeLimit = 0
        while(timeLimit < 21 && !isAuthenticated) {
            delay(3000)
            onLogin()
            timeLimit += 3
        }


        return true
    }

    private fun startNextActivity(){
        val options: ActivityOptions

        when(isAuthenticated){
            true -> {
                intent = Intent(this@SplashActivity, StartActivity::class.java)
                options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                        splashLogo, null)
            }
            else -> {
                intent = Intent(this@SplashActivity, AuthActivity::class.java)
                options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                        splashLogo, "logoTransition")
            }
        }
        window.exitTransition = null;
        startActivity(intent, options.toBundle())
        supportFinishAfterTransition()
    }

    private fun onLogin() {
        ApiService.getApi()
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {

                        when(response.code()){
                            200 -> {
                                isAuthenticated = true
                                mainContent = response.body()!!
                            }
                            403 -> {
                                isAuthenticated = false
                            }
                            else -> {
                                HelpUtils.goToast(applicationContext, "Ошибка соединения с сервером")
                                isAuthenticated = false
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        HelpUtils.goToast(applicationContext, "Ошибка соединения с интернетом")
                        isAuthenticated = false
                    }
                })
    }

}

