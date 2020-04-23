package com.example.mvvmApp.ui

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.repository.AppRepository
import com.example.mvvmApp.ui.detailpage.DetailViewModel
import com.example.mvvmApp.ui.mainpage.HomeViewModel
import javax.inject.Inject

class ViewModelFactory private constructor(
    private val app: MyApplication
) : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var appRepository: AppRepository

    init {
        app.appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(appRepository)
                isAssignableFrom(DetailViewModel::class.java) ->
                    DetailViewModel(appRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(app).also {
                    INSTANCE = it
                }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}