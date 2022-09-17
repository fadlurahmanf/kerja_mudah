package com.app.kerja_mudah.ui.service.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse

data class ServiceOrderState(
    var serviceOrderState: BaseState = BaseState.IDLE,
    var resultServiceOrder: ArrayList<ServiceOrderResponse> ?= null,
    var errorServiceOrder: String ?= null
)
