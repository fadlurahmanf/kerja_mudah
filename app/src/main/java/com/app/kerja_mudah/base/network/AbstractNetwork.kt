package com.app.kerja_mudah.base.network

import com.app.kerja_mudah.BuildConfig
import com.app.kerja_mudah.data.interceptor.TokenInterceptor
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import okhttp3.OkHttpClient

abstract class AbstractNetwork<T>(): BaseNetwork<T>() {

    override fun getBaseUrl(): String {
        return BuildConfig.BASE_DEV_URL
    }
}

abstract class  AuthAbstractNetwork<T>(
    var authRepository: AuthRepository
):AbstractNetwork<T>(){
    override fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(TokenInterceptor(authRepository))
        return super.okHttpClientBuilder(builder)
    }
}