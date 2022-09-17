package com.app.kerja_mudah.ui.freelancer.state

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse

data class FreelancerReviewState(
    var state:BaseState ?= BaseState.IDLE,
    var data:List<FreelancerResponse.Review.Detail> ?= null,
    var error:String ?= null
)
