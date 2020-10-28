package com.cookMeGood.makeItTasteIt.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Handler
import android.os.Bundle
import android.widget.Toast
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.api.ApiService
import com.cookMeGood.makeItTasteIt.api.RuntimeStorage
import com.cookMeGood.makeItTasteIt.dto.Category
import com.cookMeGood.makeItTasteIt.utils.HelpUtils
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private fun onLogin() {
        RuntimeStorage.accessToken = getSharedPreferences(RuntimeStorage.prefName, RuntimeStorage.privateMode)
                .getString("access_token", "")

        ApiService.getApi()
                .getAllCategories()
                .enqueue(object : Callback<List<Category>> {
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {

                        when(response.code()){
                            200 -> {
                                intent = Intent(applicationContext, StartActivity::class.java)
                                intent.putExtra("mainContent", response.body()!!.first())
                                startActivity(intent)
                            }
                            403 -> {
                                intent = Intent(applicationContext, AuthActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        finish()
                    }

                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        HelpUtils.goToast(applicationContext, "Ошибка соединения с сервером")
                    }
                })
    }

}

