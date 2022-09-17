package com.app.kerja_mudah.data.api.payment

import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface PaymentApi {
    @GET("api/v1/payment/method/all")
    fun getAllPaymentMethod():Observable<BaseResponse<ArrayList<PaymentMethodResponse>>>
}