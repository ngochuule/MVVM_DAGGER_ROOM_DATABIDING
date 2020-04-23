package com.example.mvvmApp.data.repository

import androidx.annotation.VisibleForTesting
import com.example.mvvmApp.data.datasource.api.APIDataSource
import com.example.mvvmApp.data.datasource.homepage.HomeDataSource
import com.example.mvvmApp.data.datasource.local.LocalDataSource

class HomePageRepositoryImpl private constructor(
    private val appRepository: AppRepository,
    private val localDataSource: LocalDataSource,
    private val apiDataSource: APIDataSource,
    private val homeDataSource: HomeDataSource
) : HomePageRepository {
    override fun showDialog() {
    }

    override fun hideDialog() {
    }

    override fun buttonAClick(value: String) {
        localDataSource.getAllTableApps()
    }

    override fun getAPIABCFinish(data: String) {
        apiDataSource.getAllTableApps()
    }

    override fun promptToStartSettingActivity() {

    }

    override fun notifyToShowSettingDialog() {
    }

    override fun onNextPageClick() {
       homeDataSource.onNextPageClick()
    }

    companion object {

        @Volatile
        private var INSTANCE: HomePageRepositoryImpl? = null

        fun getInstance(
            appRepository: AppRepository,
            localDataSource: LocalDataSource,
            apiDataSource: APIDataSource,
            homeDataSource: HomeDataSource
        ) =
            INSTANCE ?: synchronized(HomePageRepositoryImpl::class.java) {
                INSTANCE ?: HomePageRepositoryImpl(
                    appRepository,
                    localDataSource,
                    apiDataSource,
                    homeDataSource
                ).also {
                    INSTANCE = it
                }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}