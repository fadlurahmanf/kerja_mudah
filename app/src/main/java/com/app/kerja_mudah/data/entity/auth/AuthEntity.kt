package com.app.kerja_mudah.data.entity.auth

import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.auth.AuthApi
import com.google.gson.JsonObject
import javax.inject.Inject

class AuthEntity @Inject constructor(

) : AbstractNetwork<AuthApi>() {
    override fun getApi(): Class<AuthApi> {
        return AuthApi::class.java
    }

    fun login(body:JsonObject) = networkService(15).login(body)

    fun googleLogin(body: JsonObject) = networkService(15).googleLogin(body)

    fun updateFcmToken(authorization:String, body:JsonObject) = networkService(15).updateToken(authorization, body)

    fun register(body: JsonObject) = networkService(15).regis(body)

    fun activateEmail(tokenUser:String, token:String) = networkService(15).activateAccount(tokenUser, token)
}