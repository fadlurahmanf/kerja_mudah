package com.app.kerja_mudah.data.entity.service

import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.service.ServiceApi
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class ServiceEntity @Inject constructor(
    var authRepository: AuthRepository
): AbstractNetwork<ServiceApi>() {
    override fun getApi(): Class<ServiceApi> {
        return ServiceApi::class.java
    }

    fun getServiceDetail(id:Int) = networkService(15).getServiceDetail(id)

    fun orderService(body:JsonObject) = networkService(15).orderService(authorization = authRepository.accessToken?:"", body = body)

    fun applyPromoCode(body:JsonObject) = networkService(15).applyPromoCode(authorization = authRepository.accessToken?:"", body = body)

    fun getMyServiceOrder() = networkService(15).getMyServiceOrder(authorization = authRepository.accessToken?:"")
}