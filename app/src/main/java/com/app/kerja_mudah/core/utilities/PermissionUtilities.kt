package com.app.kerja_mudah.core.utilities

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionUtilities {
    fun checkSelfPermission(context:Context, permission:String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkMultiplePermission(context: Context, permissions:Array<String>):Boolean{
        val statuses:ArrayList<Boolean> = arrayListOf()
        permissions.forEach {
            statuses.add(ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED)
        }
        return !statuses.contains(false)
    }
}