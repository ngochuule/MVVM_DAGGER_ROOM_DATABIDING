package com.example.mvvmApp.data.repository

interface HomePageRepository {

    fun showDialog()
    fun hideDialog()

    fun buttonAClick(value: String)
    fun getAPIABCFinish(data: String)

    fun promptToStartSettingActivity()
    fun notifyToShowSettingDialog()

    fun onNextPageClick()
}