package com.example.mvvmApp.ui.mainpage

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmApp.data.model.HomeModel
import com.example.mvvmApp.data.datasource.db.Table1App
import com.example.mvvmApp.data.repository.AppRepository
import kotlinx.coroutines.launch
import java.text.Collator

/**
 * !!! IMPORTANT !!!
 * This is an implementation of all logic of HomeActivity and HomeFragment.
 * You MUST NOT write code depends on Android, except LiveData and ViewModel.
 * This class (all logic about view) can test by Unit Test with mock easily, without Android device or emulator.
 * You MUST keep it.
 */
class HomeViewModel(private val appRepository: AppRepository): ViewModel() {
    private val data = MutableLiveData<List<HomeModel>>()
    val items: LiveData<List<HomeModel>> = data

    init {
        val collator = Collator.getInstance()
        viewModelScope.launch {
            data.postValue(appRepository.getAllTable1()
                .filter { table1App ->
                    appRepository.isExist(table1App.tableId)
                }
                .map {
                    val tableApp = appRepository.getTable1AppById(it.tableId)
                    HomeModel(tableApp!!.tableId, tableApp.tableName, true)
                }
                .sortedWith(Comparator{a,b->
                    collator.compare(
                        a.name,
                        b.name
                    )})
                .toList())
        }
    }

    val isFirstRun = MutableLiveData<Boolean>().apply {
        value = appRepository.localDataSource.appPreferences.isFirstRun
    }

    val disableSetting = MutableLiveData<Boolean>().apply {
        value = appRepository.localDataSource.appPreferences.disableSetting
    }.also {
        it.observeForever {
            appRepository.homePageRepository.promptToStartSettingActivity()
            appRepository.homePageRepository.notifyToShowSettingDialog()
        }
    }

    val itemData = MutableLiveData<List<Table1App>>().apply {
        value = appRepository.apiDataSource.getAllTableApps()
    }

    @VisibleForTesting
    fun createItem(id: String, name: String, initChecked: Boolean): HomeModel {
        return HomeModel(id, name, initChecked)
    }

    fun onSelectionChanged(item: HomeModel){
        item.isChecked.value?.also {
            viewModelScope.launch {
                if(it){
                    val id = appRepository.insertTable1(Table1App(1,item.id, item.name))
                    if(id >0){
                       // do something
                    }
                }else{
                    val id = appRepository.deleteTable1(Table1App(1,item.id, item.name))
                }
            }
        }
    }

    suspend fun getAllData(): List<Table1App>{
        return appRepository.getAllTable1()
    }

    fun onSelectedIndex(itemIndex: Int) {
        data.value?.get(itemIndex)?.also {
            appRepository.localDataSource.getTableById(it.id)
            onSelectionChanged(it)
        }
    }

    fun onCancleSelectedIndex(itemIndex: Int) {
        data.value?.get(itemIndex)?.also {
            it.isChecked.value = !it.isChecked.value!!
        }
    }

    fun onNextPageClick(){
       appRepository.homePageRepository.onNextPageClick()
    }
}