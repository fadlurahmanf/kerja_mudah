package com.app.kerja_mudah.base.network

import androidx.annotation.Nullable
import com.app.kerja_mudah.data.interceptor.ContentTypeInterceptor
import com.app.kerja_mudah.BuildConfig
import com.app.kerja_mudah.data.interceptor.ExceptionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseNetwork<T>() {

    @Nullable
    var service:T ?= null

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    open fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder{
        return builder.addInterceptor(loggingInterceptor())
    }

    private fun provideClient(timeOut: Long): OkHttpClient {
        return okHttpClientBuilder(OkHttpClient.Builder())
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .build()
    }

    private fun providesRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
    }

    private fun provideRetrofit(timeOut: Long): Retrofit {
        return providesRetrofitBuilder().baseUrl(getBaseUrl())
            .client(provideClient(timeOut))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    open fun networkService(timeOut:Long):T{
        val retrofit = provideRetrofit(timeOut)
        service = retrofit.create(getApi())
        return service!!
    }

    abstract fun getApi(): Class<T>

    abstract fun getBaseUrl(): String
}

abstract class BaseXmlNetwork<T>() {

    @Nullable
    var service:T ?= null

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    open fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder{
        return builder.addInterceptor(loggingInterceptor())
    }

    private fun provideClient(timeOut: Long): OkHttpClient {
        return okHttpClientBuilder(OkHttpClient.Builder())
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .build()
    }

    private fun providesRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
    }

    private fun provideRetrofit(timeOut: Long): Retrofit {
        return providesRetrofitBuilder().baseUrl(getBaseUrl())
            .client(provideClient(timeOut))
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    open fun networkService(timeOut:Long):T{
        val retrofit = provideRetrofit(timeOut)
        service = retrofit.create(getApi())
        return service!!
    }

    abstract fun getApi(): Class<T>

    abstract fun getBaseUrl(): String
}