package com.example.mvvmApp.data.repository

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.api.APIDataSource
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.datasource.local.LocalDataSource

/**
 * This is an interface of main repository.
 * When you run unit tests, you can implement mock using this interface.
 */
interface AppRepository {
    val localDataSource: LocalDataSource

    val apiDataSource : APIDataSource

    val homePageRepository: HomePageRepository

    val app: MyApplication

    suspend fun getAllTable1() : List<Table1App>

    suspend fun  getTable1AppById(id: String): Table1App?

    suspend fun insertTable1(data: Table1App): Long

    suspend fun deleteTable1(data: Table1App) : Int

    suspend fun isExist(id: String) : Boolean
}