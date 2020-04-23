package com.example.mvvmApp

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.example.mvvmApp.data.repository.AppRepository
import com.example.mvvmApp.di.AppComponent
import com.example.mvvmApp.di.DaggerAppComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyApplication: Application() {

    lateinit var appComponent: AppComponent
        @VisibleForTesting set

    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this)
            .build()
        appComponent.inject(this)
        GlobalScope.launch {
            // can write some method handle logic here
//            appRepository.getAllTable1()
        }
    }
}