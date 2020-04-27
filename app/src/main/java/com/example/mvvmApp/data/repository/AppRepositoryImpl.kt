package com.example.mvvmApp.data.repository

import androidx.annotation.VisibleForTesting
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.api.APIDataSource
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.datasource.homepage.HomeDataSourceImpl
import com.example.mvvmApp.data.datasource.local.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl private constructor(
    override val app: MyApplication,
    override val localDataSource: LocalDataSource,
    override val apiDataSource: APIDataSource
) : AppRepository{


    init {
        // init data
//        localDataSource.appPreferences.initIsFirstRun = false
    }

    override val homePageRepository: HomePageRepository
        get() = HomePageRepositoryImpl.getInstance(this, localDataSource, apiDataSource, homeDataSource = HomeDataSourceImpl.getInstance(app) )

    override suspend fun getAllTable1(): List<Table1App> {
        // do nothing
        return withContext(Dispatchers.IO) {
            localDataSource.getAllTableApps()
        }
    }

    override suspend fun getTable1AppById(id: String): Table1App? {
        // do nothing
        return withContext(Dispatchers.IO) {
            localDataSource.getTableById(id)
        }
    }

    override suspend fun insertTable1(data: Table1App): Long {
        // do nothing
        return withContext(Dispatchers.IO) {
            localDataSource.insertTableAppIfNotExists(data)
        }
    }

    override suspend fun deleteTable1(data: Table1App): Int {
        // do nothing
        return withContext(Dispatchers.IO) {
            localDataSource.deleteTableApp(data)
        }
    }

    override suspend fun isExist(id: String): Boolean {
        // do nothing
        return true
    }

    companion object {

        @Volatile
        private var INSTANCE: AppRepositoryImpl? = null

        fun getInstance(app: MyApplication, localDataSource: LocalDataSource, apiDataSource: APIDataSource) =
            INSTANCE ?: synchronized(AppRepositoryImpl::class.java) {
                INSTANCE ?: AppRepositoryImpl(app, localDataSource, apiDataSource).also {
                    INSTANCE = it
                }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}