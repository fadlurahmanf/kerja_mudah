package com.app.kerja_mudah.ui.payment.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.model.payment.PaymentMethodModel

data class PaymentState(
    var getAllPaymentMethodState:BaseState = BaseState.IDLE,
    var listPaymentMethod:ArrayList<PaymentMethodModel> = arrayListOf(),
    var errorGetAllPaymentMethod:String ?= null
)
