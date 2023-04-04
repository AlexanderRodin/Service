package ru.sumin.servicestest

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        createNotificationChanel()
        startForeground(NOTIFICATION_ID, createNotification())
        log("onCreate")
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("timer: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }


    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyIntentService: $message")
    }

    private fun createNotificationChanel() {
        val notificationManager =
            getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANEL_ID,
                CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANEL_ID)
        .setContentTitle("Title")
        .setContentText("Text")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()


    companion object {
        private const val CHANEL_ID = "chanel_id"
        private const val CHANEL_NAME = "chanel_name"
        private const val NOTIFICATION_ID = 1
        private const val NAME = "MyIntentService"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}