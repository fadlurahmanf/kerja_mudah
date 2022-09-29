package com.app.kerja_mudah.core.utilities

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.nfc.NfcManager
import androidx.core.content.ContextCompat

class PermissionUtilities {
    companion object{
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

        fun checkMyGPSLocationProvider(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun checkNfcEnabled(context: Context):Boolean{
            val manager = context.getSystemService(Context.NFC_SERVICE) as NfcManager
            return manager.defaultAdapter.isEnabled
        }
    }
}