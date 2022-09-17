package com.app.kerja_mudah.data.entity.payment

import android.content.Context
import com.app.kerja_mudah.base.network.AuthAbstractNetwork
import com.app.kerja_mudah.data.api.payment.PaymentApi
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentEntity @Inject constructor(
    var context: Context,
    authRepository: AuthRepository
) : AuthAbstractNetwork<PaymentApi>(authRepository = authRepository) {
    override fun getApi(): Class<PaymentApi> {
        return PaymentApi::class.java
    }

    fun getAllPaymentMethod() = networkService(15).getAllPaymentMethod()
}