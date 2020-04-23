package com.example.mvvmApp.data.datasource.api

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.db.Table1App

class APIDataSourceImpl  private constructor(
    private val app: MyApplication
) : APIDataSource{

    companion object {

        @Volatile
        private var INSTANCE: APIDataSourceImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(APIDataSourceImpl::class.java) {
                INSTANCE ?: APIDataSourceImpl(app).also {
                    INSTANCE = it
                }
            }
    }

    override fun getAllTableApps(): List<Table1App> {
        return emptyList()
    }
}