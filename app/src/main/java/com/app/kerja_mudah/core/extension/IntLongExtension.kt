package com.app.kerja_mudah.core.extension

import java.lang.Exception
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

//fun Long.toRupiahFormat():String?{
//    try {
//        val localId = Locale("in", "ID")
//        val numberFormat = NumberFormat.getCurrencyInstance(localId)
//        return numberFormat.format(this).toString()
//    }catch (e:Exception){
//        return null
//    }
//}

fun Double.toRupiahFormat():String?{
    try {
        val localId = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localId)
        return numberFormat.format(this).toString()
    }catch (e:Exception){
        return null
    }
}

fun Int.toRupiahFormat():String?{
    try {
        val localId = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localId)
        return numberFormat.format(this).toString()
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Long
 * Output 00:00
 * */
fun Long.toDurationFormatter():String?{
    try {
        val inSecond = (this/1000).toInt()
        if (inSecond < 60){
            return "00:${if (inSecond < 10) "0$inSecond" else "$inSecond"}"
        }else{
            val inMinute = inSecond/60
            val modSecond = inSecond%60
            return "${if (inMinute < 10) "0$inMinute" else "$inMinute"}:${if (modSecond < 10) "0$modSecond" else "$modSecond"}"
        }
    }catch (e:Exception){
        return null
    }
}