package com.app.kerja_mudah.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.notification.NotificationUtils.Companion.GENERAL_CHANNEL_ID

class NotificationUtils(var context: Context) {
    var notificationCompat:NotificationManagerCompat = NotificationManagerCompat.from(context)

    companion object{
        const val GENERAL_CHANNEL_ID = "GENERAL_CHANNEL_ID"
        const val GENERAL_CHANNEL_NAME = "General"
    }

    init {
        createGeneralNotificationChannel()
    }

    private fun createGeneralNotificationChannel(){
        var generalChannel : NotificationChannel ? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            generalChannel = NotificationChannel(GENERAL_CHANNEL_ID, GENERAL_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "General Notification"
            }
        } else {
            null
        }
        if (generalChannel != null){
            notificationCompat.createNotificationChannel(generalChannel)
        }else{
            Log.e("Notification Utils", "General channel is null")
        }
    }

    fun basicNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, GENERAL_CHANNEL_ID)
            .setSmallIcon(R.drawable.english) /** Config Later */
    }
}