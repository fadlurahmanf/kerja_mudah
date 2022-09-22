package com.app.kerja_mudah.ui.freelancer.state

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReviewResponse

data class FreelancerReviewState(
    var state:BaseState ?= BaseState.IDLE,
    var data:FreelancerReviewResponse ?= null,
    var error:String ?= null
)
