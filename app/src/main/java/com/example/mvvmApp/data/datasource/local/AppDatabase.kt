package com.example.mvvmApp.data.datasource.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.db.Table1App

@Database(entities = arrayOf(Table1App::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun appsDao(): AppDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(AppDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(app, AppDatabase::class.java, "myApp.db")
                    .build().also {
                        INSTANCE = it
                    }
            }
    }
}