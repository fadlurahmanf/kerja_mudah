package com.app.kerja_mudah.core.extension

import android.content.ContentValues.TAG
import android.util.Base64
import android.util.Log
import com.app.kerja_mudah.BuildConfig
import java.nio.charset.Charset
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.Exception
import kotlin.collections.ArrayList

fun String.encrypt() : String?{
    try {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = BuildConfig.ENCRYPT_DECRYPT_PASSWORD.toByteArray(Charset.forName("UTF-8"))
        messageDigest.update(bytes, 0, bytes.size)
        val key = messageDigest.digest()
        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedByteArray = cipher.doFinal(this.toByteArray())
        return Base64.encodeToString(encryptedByteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        return null
    }
}

fun String.decrypt() : String?{
    try {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = BuildConfig.ENCRYPT_DECRYPT_PASSWORD.toByteArray(Charset.forName("UTF-8"))
        messageDigest.update(bytes, 0, bytes.size)
        val key = messageDigest.digest()
        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val decodeValue = Base64.decode(this, Base64.DEFAULT)
        val decryptByte = cipher.doFinal(decodeValue)
        return String(decryptByte)
    }catch (e: Exception){
        return null
    }
}

/**
 * FROM [ 2022-09-23 16:13:22 ]
 * TO [ DATE ]
 * */
fun String.toDate(): Date? {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.parse(this)
    }catch (e:Exception){
        return null;
    }
}

fun String.isoDateTimeToDate(): Date? {
    try {
        if (!this.contains("T", ignoreCase = true) && !this.contains("Z", ignoreCase = true)) return null
        val date = this.split("T").first()
        val time = this.split("T").last().replace("Z", "")
        val calendar = Calendar.getInstance()
        calendar.apply {
            if (date.split("-").size != 3){
                return null
            }else{
                set(Calendar.YEAR, date.split("-").first().toInt())
                set(Calendar.MONTH, (date.split("-")[1].toInt())-1)
                set(Calendar.DAY_OF_MONTH, date.split("-")[2].toInt())
            }
            set(Calendar.HOUR_OF_DAY, time.split(":").first().toInt())
            set(Calendar.MINUTE, time.split(":")[1].toInt())
            set(Calendar.SECOND, 0)
        }
        calendar.add(Calendar.HOUR_OF_DAY, 7) // UTC TIME ZONE ADD +7 TO INDONESIAN DATE
        return calendar.time
    } catch (e: Exception) {
        Log.e(TAG, "isoDateTimeToDate: ${e.message}")
        return null
    }
}

fun String.hideEmail(): String {
    try {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()) return this
        val extensionMail : String = "@${this.split("@").last()}"
        val prefixMail: String = this.split("@").first()
        var hiddenEmail : String = "";
        prefixMail.split("").forEachIndexed { index, s ->
            if (prefixMail.length <= 3){
                if (index == 0){
                    hiddenEmail += s
                }else{
                    hiddenEmail += "*"
                }
            }else{
                if (index <=3){
                    hiddenEmail += s
                }else{
                    hiddenEmail += "*"
                }
            }
        }
        hiddenEmail += extensionMail
        return hiddenEmail
    }catch (e:Exception){
        Log.e("hideEmail", e.message?:"")
        return this
    }
}

/**
 * Input 2022-12-01
 * Output Calendar
 * */
fun String.toCalendar():Calendar?{
    try {
        if (this.split("-").size == 3){
            val splitDate = this.split("-")
            val calendar = Calendar.getInstance()
            return calendar.apply {
                set(Calendar.DAY_OF_MONTH, splitDate.last().toInt())
                set(Calendar.MONTH, splitDate[1].toInt()-1)
                set(Calendar.YEAR, splitDate.first().toInt())
            }
        }else{
            return null
        }
    }catch (e:Exception){
        return null
    }
}

/**
 * Input 2022-01-01 14:30:30
 * Output Calendar
 */
fun String.toCalendar2():Calendar?{
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }catch (e:Exception){
        return null
    }
}

/**
 * Input [ http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/.... ]
 * Output [ String CacheKey ]
 * */
fun String.toCacheKeyFromPublicVideo():String?{
    try {
        return split("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/").last()
    }catch (e:Exception){
        Log.e("StringExtension", "${e.message}")
        return null
    }
}

fun String.changeFirstWordToUpperCase():String{
    try {
        var newString = ""
        val list:List<String> = split("")
        for (element in 0 until list.size-1){
            if (element == 1){
                newString += list[element].uppercase()?:""
            }else{
                newString += list[element]?:""
            }
        }
        return newString
    }catch (e:Exception){
        return this
    }
}