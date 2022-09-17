package com.app.kerja_mudah.core.fcm

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.app.kerja_mudah.BaseApp
import com.app.kerja_mudah.core.notification.NotificationUtils
import com.app.kerja_mudah.core.receiver.CoreReceiver
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.response.core.NotificationResponse
import com.app.kerja_mudah.di.component.CoreComponent
import com.app.kerja_mudah.ui.chat.ChatRoomActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.random.Random

class FcmService:FirebaseMessagingService() {

    private lateinit var notificationUtils :NotificationUtils

    companion object{
        val TAG = FcmService::class.java.simpleName
    }

    @Inject
    lateinit var authRepository: AuthRepository

    private lateinit var component:CoreComponent
    override fun onCreate() {
        component = (applicationContext as BaseApp).applicationComponent.coreComponent().create()
        component.inject(this)
        super.onCreate()
        notificationUtils = NotificationUtils(applicationContext)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG, "TITLE: ${message.notification?.title}\nBODY: ${message.notification?.body}\nData: ${message.data}")
        if (message.data["type"] == "chat"){
            handleChatNotification(message)
        }else if (message.data["type"] == "order-service"){
            handleOrderServiceNotification(message)
        }
    }

    private fun handleChatNotification(message: RemoteMessage) {
        try {
            val notificationResponse : NotificationResponse<NotificationResponse.Chat> = NotificationResponse(
                data = NotificationResponse.Chat(
                    opponentId = message.data["opponent_id"],
                    opponentFullName = message.data["opponent_full_name"],
                    opponentPhoto = message.data["opponent_photo"]
                ),
                notification = NotificationResponse.Notification(
                    title = message.notification?.title,
                    body = message.notification?.body
                )
            )

            val navigateIntent = Intent(applicationContext, ChatRoomActivity::class.java)
            navigateIntent.putExtra(ChatRoomActivity.TO, notificationResponse.data?.opponentId?.toInt())
            Log.i(TAG, "MASUK WOI ${notificationResponse.data?.opponentId?.toInt()?.toString()}")
            Log.i(TAG, "MASUK WOI ${navigateIntent.getIntExtra(ChatRoomActivity.TO, -1)}")
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, navigateIntent, PendingIntent.FLAG_MUTABLE)

            val intent = Intent(applicationContext, CoreReceiver::class.java)
            intent.putExtra(CoreReceiver.NEW_CHAT, message.notification?.body)
            sendBroadcast(intent)

            notificationUtils.notificationCompat.notify(
                Random.nextInt(100), notificationUtils.basicNotification().setContentTitle(message.notification?.title?:"")
                    .setContentText(message.notification?.body?:"")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            )
        }catch (e:Exception){
            Log.e(TAG, "handleChatNotification: ${e.message}")
        }
    }

    private fun handleOrderServiceNotification(message: RemoteMessage) {
//        if (authRepository.isLoggedIn){
//            authRepository.totalUnreadOrderService = (authRepository.totalUnreadOrderService?:0) + 1
//            val intent = Intent(applicationContext, CoreReceiver::class.java)
//            intent.putExtra(CoreReceiver.TOTAL_UNREAD_ORDER_SERVICE, authRepository.totalUnreadOrderService?:0)
//            sendBroadcast(intent)
//        }
//
//        val notificationData : NotificationResponse<Nullable> = NotificationResponse(
//            data = NotificationResponse.Data(
//                id = message.data["id"],
//                title = message.data["title"],
////                body = Gson().fromJson("""${message.data["body"]}""", NotificationResponse.Data.Chat::class.java),
//                message = message.data["message"]
//            ),
//            notification = NotificationResponse.Notification(
//                title = message.notification?.title,
//                body = message.notification?.body
//            )
//        )
//
//        notificationUtils.notificationCompat.notify(
//            Random.nextInt(100), notificationUtils.basicNotification().setContentTitle(notificationData.data?.title?:"")
//                .setContentText(notificationData.data?.message?:"")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//                .build()
//        )
    }
}