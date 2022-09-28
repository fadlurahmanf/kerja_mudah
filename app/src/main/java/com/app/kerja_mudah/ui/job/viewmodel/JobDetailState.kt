package com.app.kerja_mudah.ui.job.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.job.JobDetailResponse
import com.app.kerja_mudah.data.response.job.JobResponse

data class JobDetailState(
    var getDetailJobState:BaseState = BaseState.IDLE,
    var detailJob:JobDetailResponse ?= null,
    var errorGetDetailJob:String ?= null,

    var getListJobByCategoryIdState:BaseState = BaseState.IDLE,
    var listJobByCategoryId:List<JobResponse> ?= null,
    var errorGetListJobByCategoryId:String ?= null
)
