package com.app.kerja_mudah.data.model.payment

import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse

data class PaymentMethodModel(
    var paymentMethodName:String ?= null,
    var method:PaymentMethodResponse ?= null
)