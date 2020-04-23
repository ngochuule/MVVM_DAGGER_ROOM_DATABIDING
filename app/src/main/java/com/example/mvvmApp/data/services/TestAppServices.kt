package com.example.mvvmApp.data.services

import android.app.IntentService
import android.content.Intent
import com.example.mvvmApp.MyApplication

class TestAppServices: IntentService(TestAppServices::class.simpleName) {
    override fun onHandleIntent(intent: Intent?) {
        val app = application as MyApplication
        app.appComponent.inject(this)
    }
}