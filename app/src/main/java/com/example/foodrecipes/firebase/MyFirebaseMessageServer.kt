package com.example.foodrecipes.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.foodrecipes.R
import com.example.foodrecipes.util.Constants.Companion.FIREBASE_NOTIFICATION_CHANNEL_ID
import com.example.foodrecipes.util.Constants.Companion.FIREBASE_NOTIFICATION_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageServer : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        createNotfication(message.notification?.title,message.notification?.body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun createNotfication(title:String?,message:String?){
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    FIREBASE_NOTIFICATION_CHANNEL_ID,
                    "firebase通知",
                    NotificationManager.IMPORTANCE_DEFAULT))
            NotificationCompat.Builder(applicationContext,FIREBASE_NOTIFICATION_CHANNEL_ID)
        }else{
            NotificationCompat.Builder(applicationContext)
        }.apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(title?: "")
            setContentText(message?: "")
        }.build()
        notificationManager.notify(FIREBASE_NOTIFICATION_ID,notification)
    }

}