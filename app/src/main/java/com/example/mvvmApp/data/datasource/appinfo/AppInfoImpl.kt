package com.example.mvvmApp.data.datasource.appinfo

import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.VisibleForTesting
import com.example.mvvmApp.MyApplication

class AppInfoImpl  private constructor(
    private val app: MyApplication
) : AppInfo{
    override fun isAvailable(appId: String): Boolean {
        try {
            val appInfo = app.packageManager.getApplicationInfo(appId, 0)
            return appInfo.enabled
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }

    override fun getInstalledApplications(): List<AppInfo.AppInformation> {
        val intent = Intent(Intent.ACTION_MAIN).also {
            it.addCategory(Intent.CATEGORY_LAUNCHER)
        }
        return app.packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            .mapNotNull { it.activityInfo }
            .filter { it.packageName != app.packageName }
            .filter { it.enabled }
            .map {
                AppInfo.AppInformation(
                    it.packageName,
                    it.name
                )
            }
            .toList()
    }

    override fun getApplicationName(appId: String): String {
        try {
            val appInfo = app.packageManager.getApplicationInfo(appId, 0)
            return app.packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            throw IllegalArgumentException()
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppInfoImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(AppInfoImpl::class.java) {
                INSTANCE ?: AppInfoImpl(app).also {
                    INSTANCE = it
                }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}