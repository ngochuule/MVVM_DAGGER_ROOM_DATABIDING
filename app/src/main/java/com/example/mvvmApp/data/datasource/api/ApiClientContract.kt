package com.example.mvvmApp.data.datasource.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface ApiClientContract {
    fun setConnection(apiConnection: ApiConnection)

    //fun buildOkHttp3Client(): OkHttpClient

    fun buildRetrofit2Client(okHttpClient: OkHttpClient, apiBaseUrl: String): Retrofit
}