package com.example.mvvmApp.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.repository.AppRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver(){
    @Inject
    lateinit var appRepository: AppRepository
    override fun onReceive(context: Context?, intent: Intent?) {
        val app = context!!.applicationContext as MyApplication
        app.appComponent.inject(this)

        when (intent?.action) {
            NotificationsImpl.NOTIFICATION_ACTION_CLICK_BUTTON ->{
                runBlocking {
                    appRepository.homePageRepository.buttonAClick("")
                }
            }
            NotificationsImpl.NOTIFICATION_ACTION_CLICK_BUTTON ->{
                runBlocking {
                    // value pass notification
                    val value: String = intent.getStringExtra(
                        NotificationsImpl.NOTIFICATION_VALUE_PASS
                    )!!
                    appRepository.homePageRepository.buttonAClick(value)
                }
            }
            NotificationsImpl.NOTIFICATION_ACTION_CLICK_BUTTON ->{
                runBlocking {
                    // value pass notification
                    val value: String = intent.getStringExtra(
                        NotificationsImpl.NOTIFICATION_VALUE_PASS
                    )!!
                    appRepository.homePageRepository.buttonAClick(value)
                }
            }
        }
    }
}