package com.app.kerja_mudah.data.interceptor

import com.app.kerja_mudah.data.repository.auth.AuthRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    var authRepository: AuthRepository
) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(addHeader(chain.request()))
    }

    private fun addHeader(oriRequest: Request): Request {
        return oriRequest.newBuilder().addHeader("Authorization", authRepository.accessToken?:"").build()
    }
}