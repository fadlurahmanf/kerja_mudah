package com.app.kerja_mudah.core.extension

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Input Date
 * Output Jun 15, 2020
 * */
fun Date.formatDate():String?{
    try {
        val sdf = SimpleDateFormat("MMM d, yyyy")
        return sdf.format(this)
    }catch (e: Exception){
        return null
    }
}

/**
 * Output Variative
 * */
fun Date.formatDate2():String?{
    try {
        val currentDate = Date()
        val diffInMillisecond = currentDate.time - this.time
        val diffInDays: Long = TimeUnit.MILLISECONDS.toDays(diffInMillisecond)
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMillisecond)
        val diffInMin: Long = TimeUnit.MILLISECONDS.toMinutes(diffInMillisecond)
        val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMillisecond)
        if (diffInDays > 0){
            /** Output [ 11-05-2022 ] */
            val sdf = SimpleDateFormat("d-MM-yyyy")
            return sdf.format(this)
        }else if (diffInHours > 0 || diffInMin > 0){
            /** Output [ 08:40 ] */
            val sdf = SimpleDateFormat("HH:mm")
            return sdf.format(this)
        }else if (diffInSec > 0){
            return "just now"
        }else{
            /** Output [ 11-05-2022 ] */
            val sdf = SimpleDateFormat("d-MM-yyyy")
            return sdf.format(this)
        }
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Date
 * Output 2022-12-01
 * */
fun Date.formatDate3():String?{
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(this)
    }catch (e: Exception){
        return null
    }
}

/**
 * Input Date
 * Output 08:40
 * */
fun Date.formatDate4(): String? {
    try {
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Date
 * Output 2020-01-30 08:40:00
 * */
fun Date.formatDate5(): String? {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}