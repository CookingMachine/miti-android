package com.cookMeGood.makeItTasteIt

import android.app.Application
import androidx.room.Room
import com.database.AppDatabase

class App: Application() {

    private lateinit var database: AppDatabase

    companion object {
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
                .build()
    }

    fun getDataBase() = database
}