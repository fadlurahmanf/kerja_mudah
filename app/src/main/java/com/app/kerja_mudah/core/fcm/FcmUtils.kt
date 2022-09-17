package com.app.kerja_mudah.core.fcm

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Exception

class FcmUtils {
    private var instance = FirebaseMessaging.getInstance()

    fun getNewToken(): Task<String> {
        return instance.token
    }
}