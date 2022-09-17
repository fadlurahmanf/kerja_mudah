package com.app.kerja_mudah.ui.service.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse

data class DetailServiceState(
    var getDetailState : BaseState ?= BaseState.IDLE,
    var serviceDetailData : ServiceDetailResponse ?= null,
    var errorGetDetail: String ?= null,

    var orderState: BaseState ?= BaseState.IDLE,
    var errorOrder: String ?= null
)
