package com.cookMeGood.makeItTasteIt.activity

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.api.ApiService
import com.api.dto.response.UserResponse
import com.cookMeGood.makeItTasteIt.R
import com.cookMeGood.makeItTasteIt.container.DataContainer
import com.cookMeGood.makeItTasteIt.utils.ContextUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : SuperActivity() {

    private val waitForResponseCoroutine = CoroutineScope(Dispatchers.Main)
    private var isAuthenticated: Boolean = false

    override fun setAttr() = setLayout(R.layout.activity_splash)

    override fun initInterface() {

        waitForResponseCoroutine.launch {
            val call = async { validateAccessToken() }

            try {
                call.await()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@SplashActivity,
                    "Server is unavailable\nTry again later",
                    Toast.LENGTH_SHORT
                ).show()
            }
            startNextActivity()
        }
    }

    private fun validateAccessToken(): Boolean {

        val sharedPreferences =
            applicationContext.getSharedPreferences(ApiService.PREF_NAME, Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString(ApiService.ACCESS_TOKEN_KEY, "") ?: ""

        if (jwtToken.isNotEmpty()) {

            ApiService.getApi().validateToken("Bearer $jwtToken")
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            isAuthenticated = true
                            DataContainer.currentUser = response.body()!!
                            ApiService.jwtToken = DataContainer.currentUser!!.jwtToken
                        } else {
                            isAuthenticated = false
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        ContextUtils.goLongToast(applicationContext, t.message.toString())
                    }
                })
        } else {
            return false
        }

        return true
    }

    private fun startNextActivity() {
        when (isAuthenticated) {
            true -> {
                DataContainer
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
