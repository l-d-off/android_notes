package com.darf.android.uni1

import android.app.Application
import androidx.room.Room

class App : Application() {
//    private lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        database = Room.databaseBuilder(
//            this, AppDatabase::class.java,
//            "database",
//        ).build()
    }

//    internal fun getDatabase() = database
}