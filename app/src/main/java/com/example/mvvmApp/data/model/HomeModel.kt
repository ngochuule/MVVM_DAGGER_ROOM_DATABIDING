package com.example.mvvmApp.data.model

import androidx.lifecycle.MutableLiveData

class HomeModel(private val id_: String, private val name_ :String, private var isChecked_: Boolean) {
    // can create Table1App here
    // class HomeModel(private val table1 Table1App, private var isChecked_: Boolean)
    val id: String = id_
    val name: String = name_
//    val isChecked: Boolean = isChecked_
    val isChecked = MutableLiveData(isChecked_)
}