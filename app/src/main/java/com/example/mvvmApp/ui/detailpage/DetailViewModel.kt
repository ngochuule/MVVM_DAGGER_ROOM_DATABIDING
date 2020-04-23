package com.example.mvvmApp.ui.detailpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.repository.AppRepository
import kotlinx.coroutines.launch

class DetailViewModel (
    private val appRepository: AppRepository
) : ViewModel(){

    private val isFirstRun = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isFirstRun.value = appRepository.localDataSource.appPreferences.isFirstRun
        }
    }

    val disableSetting = MutableLiveData<Boolean>()

    val items = MutableLiveData<List<Table1App>>().apply {
        value = appRepository.apiDataSource.getAllTableApps()
    }

    val itemsBackup = MutableLiveData<List<Table1App>>().apply {
        value = appRepository.apiDataSource.getAllTableApps()
    }.also {
        it.observeForever {
            // set list data
//            appRepository.localDataSource.appPreferences.listApp = it
        }
    }
}