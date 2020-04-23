package com.example.mvvmApp.di

import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.notification.NotificationReceiver
import com.example.mvvmApp.data.provider.AppContentProvider
import com.example.mvvmApp.data.services.TestAppServices
import com.example.mvvmApp.ui.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MyApplication): Builder

        fun build(): AppComponent

        fun appModule(appModule: AppModule): Builder
    }

    fun inject(testAppServices: TestAppServices)
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(appContentProvider: AppContentProvider)
    fun inject(notificationReceiver: NotificationReceiver)
}