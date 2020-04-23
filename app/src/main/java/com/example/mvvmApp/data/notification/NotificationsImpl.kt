package com.example.mvvmApp.data.notification

import android.app.PendingIntent
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.R

class NotificationsImpl private constructor(
    private val app: MyApplication
) : Notifications{
    override fun showNotification(value: String) {
        val notification = NotificationCompat.Builder(app, CHANNEL_NOTIFICATION_APP)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(app.resources.getText(R.string.notifi_value))
            .setGroup(CHANNEL_NOTIFICATION_APP)
            .build()

        NotificationManagerCompat.from(app).notify(NOTIFICATION_APP_ID, notification)
    }

    override fun updateNotification(status: Int) {
        val layoutResId: Int = R.layout.layout_notification_popup
        val remoteViews = RemoteViews(app.packageName, layoutResId).also {
            if(status ==0){
                it.setViewVisibility(R.id.textView, View.VISIBLE)
                it.setTextViewText(R.id.textView, "ABC 123")
            }else if (status ==1){
                it.setViewVisibility(R.id.textView, View.GONE)
            }else if(status ==2){
                it.setViewVisibility(R.id.textView, View.VISIBLE)
                it.setTextViewText(R.id.textView, "ABC")
                it.setOnClickPendingIntent(
                    R.id.button,
                    PendingIntent.getBroadcast(app,
                        NOTIFICATION_ACTION_CLICK_BUTTON_ID,
                        Intent(app, NotificationReceiver::class.java).also {
                            it.action = NOTIFICATION_ACTION_CLICK_BUTTON
                            it.putExtra(NOTIFICATION_VALUE_PASS, "value")
                        },
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                )
            }else{
                it.setViewVisibility(R.id.textView, View.GONE)
            }
        }
    }

    override fun cancelNotification() {

    }

    companion object {

        const val CHANNEL_NOTIFICATION_APP ="notification_app_data1"
        const val NOTIFICATION_APP_ID = 0x100

        const val NOTIFICATION_ACTION_CLICK_BUTTON ="NOTIFICATION_ACTION_CLICK_BUTTON"
        const val NOTIFICATION_ACTION_CLICK_BUTTON_ID = 0x99

        const val NOTIFICATION_VALUE_PASS ="NOTIFICATION_VALUE_PASS"

        @Volatile
        private var INSTANCE: NotificationsImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(NotificationsImpl::class.java) {
                INSTANCE ?: NotificationsImpl(app).also {
                    INSTANCE = it
                }
            }
    }
}