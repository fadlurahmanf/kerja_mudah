package com.app.kerja_mudah.data.interceptor

import android.util.Log
import androidx.annotation.Nullable
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import okio.Okio
import org.json.JSONObject
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException

class ExceptionInterceptor : Interceptor {
    companion object{
        val TAG = ExceptionInterceptor::class.java.simpleName
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val rawResponse = JSONObject(response.peekBody(Long.MAX_VALUE).string())
            if (response.code == 401){
                throw IOException(rawResponse.optString("message", ""))
            } else if(response.code == 500){
                throw IOException(rawResponse.optString("message"))
            }else if (response.code == 404){
                throw IOException("URL NOT FOUND")
            } else {
                return response
            }
        }catch (e:Exception){
            if (e is SocketTimeoutException){
                throw IOException("Failed to connect with server. Please try again later!")
            } else if (e is ConnectException){
                throw IOException("Failed to connect. Check your internet connection!")
            } else if (e is IOException){
                throw e
            } else {
                throw e
            }
        }
    }
}