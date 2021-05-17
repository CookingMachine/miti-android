package com.database

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.database.model.RecipeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataBaseInstrumentedTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDataBase() {
        database = Room.inMemoryDatabaseBuilder(
                            InstrumentationRegistry.getInstrumentation().context,
                            AppDatabase::class.java)
                        .build()
    }

    @After
    fun closeDataBase() {
        database.close()
    }
}