package com.app.kerja_mudah.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CoreReceiver:BroadcastReceiver() {
    companion object{
        const val CORE_RECEIVER = "CORE_RECEIVER"
        const val TOTAL_UNREAD_ORDER_SERVICE = "TOTAL_UNREAD_ORDER_SERVICE"
        const val NEW_CHAT = "NEW_CHAT"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val newIntent = Intent(CORE_RECEIVER)
        if ((intent?.extras?.getInt(TOTAL_UNREAD_ORDER_SERVICE, 0)?:0) > 0){
            newIntent.putExtra(TOTAL_UNREAD_ORDER_SERVICE, intent?.extras?.getInt(TOTAL_UNREAD_ORDER_SERVICE, 0))
        }
        if ((intent?.extras?.getString(NEW_CHAT)) != null){
            newIntent.putExtra(NEW_CHAT, intent.extras?.getString(NEW_CHAT))
        }
        context?.sendBroadcast(newIntent)
    }
}