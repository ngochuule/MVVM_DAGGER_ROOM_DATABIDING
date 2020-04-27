package com.example.mvvmApp.data.datasource.local

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.appinfo.AppInfoImpl
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.preferences.AppPreferences
import com.example.mvvmApp.data.preferences.AppPreferencesImpl

/**
 * This class MUST be kept simple, it SHOULD NOT include logic.
 * The logic SHOULD be written in AppRepositoryImpl.
 */

class LocalDataSourceImpl private constructor(
    private val app: MyApplication
) : LocalDataSource{

    private val appDb = AppDatabase.getInstance(app)
    override val appPreferences: AppPreferences
        get() = AppPreferencesImpl.getInstance(app)
    override val appInfo = AppInfoImpl.getInstance(app)

    init {
        appPreferences.initIsFirstRun = false
    }

    override fun getAllTableApps(): List<Table1App> {
       return appDb.appsDao().getAll()
    }

    override fun getTableById(tableId: String): Table1App? {
        return appDb.appsDao().getById(tableId)
    }

    override fun insertTableAppIfNotExists(table: Table1App) = appDb.appsDao().insertIfNotExists(table)

    override fun deleteTableApp(table: Table1App): Int {
        return appDb.appsDao().delete(table)
    }

    companion object {

        @Volatile
        private var INSTANCE: LocalDataSourceImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(LocalDataSourceImpl::class.java) {
                INSTANCE ?: LocalDataSourceImpl(app).also {
                    INSTANCE = it
                }
            }
    }
}