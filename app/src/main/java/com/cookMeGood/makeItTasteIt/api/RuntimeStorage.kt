package com.cookMeGood.makeItTasteIt.api

object RuntimeStorage {

    var privateMode = 0
    val prefName = "access_preferences"

    var accessToken : String? = null

    fun updateToken(token: String){
        accessToken = "Bearer $token"
    }
}