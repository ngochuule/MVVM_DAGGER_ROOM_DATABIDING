package com.example.mvvmApp.di

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.api.APIDataSourceImpl
import com.example.mvvmApp.data.datasource.local.LocalDataSourceImpl
import com.example.mvvmApp.data.repository.AppRepository
import com.example.mvvmApp.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
@Module
class AppModule {
    @Singleton
    @Provides
    open fun provideRepository(app: MyApplication): AppRepository = AppRepositoryImpl.getInstance(
        app,
        LocalDataSourceImpl.getInstance(app),
        APIDataSourceImpl.getInstance(app)
    )
}