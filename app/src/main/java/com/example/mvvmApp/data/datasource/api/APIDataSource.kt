package com.example.mvvmApp.data.datasource.api

import com.example.mvvmApp.data.datasource.db.Table1App

interface APIDataSource {

    fun getAllTableApps(): List<Table1App>
}