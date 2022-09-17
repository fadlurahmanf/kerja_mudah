package com.app.kerja_mudah.ui.payment.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.service.ServicePromoCodeResponse

data class CheckoutState(
    var orderServiceState:BaseState = BaseState.IDLE,
    var errorOrderService:String ?= null,

    var applyPromoState:BaseState = BaseState.IDLE,
    var resultApplyPromo:ServicePromoCodeResponse ?= null,
    var errorApplyPromo:String ?= null
)
