package com.example.mvvmApp.data.datasource.appinfo

interface AppInfo {
    data class AppInformation(val appId: String, val className: String)

    fun isAvailable(appId: String) : Boolean
    fun getInstalledApplications(): List<AppInformation>
    fun getApplicationName(appId: String): String
}