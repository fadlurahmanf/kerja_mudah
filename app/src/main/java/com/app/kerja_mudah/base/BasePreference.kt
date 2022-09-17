package com.app.kerja_mudah.base

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.core.extension.decrypt
import com.app.kerja_mudah.core.extension.encrypt
import com.google.gson.Gson
import org.json.JSONArray
import java.lang.Exception

abstract class BasePreference(context: Context) {
    private var sharedPreferences: SharedPreferences ?= null

    private val TAG = BasePreference::class.java.simpleName

    init {
        sharedPreferences = context.getSharedPreferences(ParamsKeySP.PARAMS_SP_KEY, Context.MODE_PRIVATE)
    }

    protected fun saveString(key:String, value:String){
        if (value.encrypt() != null){
            sharedPreferences?.edit()?.putString(key, value.encrypt())?.apply()
        }
    }

    protected fun getString(key: String):String?{
        return sharedPreferences?.getString(key, null)?.decrypt()
    }

    protected fun saveInt(key: String, value: Int){
        if (value.toString().encrypt() != null){
            sharedPreferences?.edit()?.putString(key, value.toString().encrypt())?.apply()
        }
    }

    protected fun getInt(key: String):Int?{
        return try {
            val raw = sharedPreferences?.getString(key, null)?.decrypt()
            when{
                raw != null -> raw.toInt()
                else -> null
            }
        }catch (e: Exception){
            null
        }
    }

    protected fun saveLong(key: String, value: Long){
        if (value.toString().encrypt() != null){
            sharedPreferences?.edit()?.putString(key, value.toString().encrypt())?.apply()
        }
    }

    protected fun getLong(key: String):Long?{
        return try {
            val raw = sharedPreferences?.getString(key, null)?.decrypt()
            when{
                raw != null -> raw.toLong()
                else -> null
            }
        }catch (e: Exception){
            null
        }
    }

    protected fun saveFloat(key: String, value: Float){
        if (value.toString().encrypt() != null){
            sharedPreferences?.edit()?.putString(key, value.toString().encrypt())?.apply()
        }
    }

    protected fun getFloat(key: String):Float?{
        return try {
            val raw = sharedPreferences?.getString(key, null)?.decrypt()
            when{
                raw != null -> raw.toFloat()
                else -> null
            }
        }catch (e: Exception){
            null
        }
    }

    protected fun saveDouble(key: String, value: Double){
        if (value.toString().encrypt() != null){
            sharedPreferences?.edit()?.putString(key, value.toString().encrypt())?.apply()
        }
    }

    protected fun getDouble(key: String):Double?{
        return try {
            val raw = sharedPreferences?.getString(key, null)?.decrypt()
            when{
                raw != null -> raw.toDouble()
                else -> null
            }
        }catch (e: Exception){
            null
        }
    }

    protected fun <T> saveData(key: String, value:T?){
        if (!Gson().toJson(value).encrypt().isNullOrEmpty()){
            sharedPreferences?.edit()?.putString(key, Gson().toJson(value).encrypt())?.apply()
        }
    }

    protected fun <T> getData(key: String, classOfT:Class<T>): T?{
        return try {
            val rawString:String ?= getString(key)
            if (rawString != null){
                Gson().fromJson(rawString, classOfT)
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

    protected fun <T> saveListData(key: String, value: List<T>){
        if (!Gson().toJson(value).encrypt().isNullOrEmpty()){
            sharedPreferences?.edit()?.putString(key, Gson().toJson(value).encrypt())?.apply()
        }
    }

    protected fun <T> getListData(key: String, classOfT:Class<T>): ArrayList<T>?{
        try {
            val rawString:String? = getString(key)
            val list:ArrayList<T> = arrayListOf<T>()
            list.clear()
            if (rawString!=null){
                val jsonArray = JSONArray(rawString)
                for (i in 0 until jsonArray.length()){
                    val row = jsonArray.getJSONObject(i)
                    list.add(Gson().fromJson(row.toString(), classOfT))
                }
                return list
            }else{
                return null
            }
        }catch (e: Exception){
            Log.e(TAG, "getListData: -> ${e.message}")
            return null
        }
    }

    protected fun getListString(key: String): ArrayList<String>?{
        try {
            val rawString:String? = getString(key)
            val list:ArrayList<String> = arrayListOf<String>()
            list.clear()
            if (rawString!=null){
                val jsonArray = JSONArray(rawString)
                for (i in 0 until jsonArray.length()){
                    val row = jsonArray.getString(i)
                    list.add(row)
                }
                return list
            }else{
                return null
            }
        }catch (e: Exception){
            Log.e(TAG, "getListString: -> ${e.message}")
            return null
        }
    }

    protected fun clearData(key: String){
        sharedPreferences?.edit()?.remove(key)?.apply()
    }
}
