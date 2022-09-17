package com.app.kerja_mudah.ui.home.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.core.AdsResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.response.job.JobResponse

data class HomeState(
    var jobCategoryState:BaseState ?= BaseState.IDLE,
    var dataJobCategory:List<JobCategoryResponse> ?= null,
    var errorMessageJobCategory:String ?= null,

    var allFreelancerState:BaseState ?= BaseState.IDLE,
    var dataAllFreelancer:List<FreelancerResponse> ?= null,
    var errorMessageAllFreelancer:String ?= null,

    var adsBannerState:BaseState ?= BaseState.IDLE,
    var dataAdsBanner:List<AdsResponse> ?= null,
    var errorMessageAdsBanner:String ?= null,

    var allJobState:BaseState ?= BaseState.IDLE,
    var dataAllJob:List<JobResponse> ?= null,
    var errorAllJob:String ?= null,

    var reelsFreelancerState:BaseState = BaseState.IDLE,
    var listReelFreelancer:ArrayList<FreelancerReelResponse> ?= null,
    var errorReelsFreelancer:String ?= null
)
