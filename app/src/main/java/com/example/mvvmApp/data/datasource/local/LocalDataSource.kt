package com.example.mvvmApp.data.datasource.local

import com.example.mvvmApp.data.datasource.appinfo.AppInfo
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.preferences.AppPreferences

interface LocalDataSource {
    val appPreferences: AppPreferences
    val appInfo: AppInfo

    fun getAllTableApps(): List<Table1App>
    fun getTableById(tableId: String): Table1App?
    fun insertTableAppIfNotExists(table: Table1App): Int
    fun deleteTableApp(table: Table1App): Int
}