package com.app.kerja_mudah.core.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.hideKeyboard(view:View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

/**
 * Output [ 2022-05-14T03:07:06.467Z ]
 * */
fun Context.getCurrentUtcIso8601(): String? {
    try {
        val tz: TimeZone = TimeZone.getTimeZone("UTC")
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        df.timeZone = tz
        return df.format(Date())
    }catch (e:Exception){
        return null
    }
}