package com.example.mvvmApp.data.notification

interface Notifications {

    fun showNotification(value: String)
    fun updateNotification(status: Int)
    fun cancelNotification()
}