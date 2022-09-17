package com.app.kerja_mudah.ui.freelancer.state

import com.app.kerja_mudah.base.BaseState

data class RegisterFreelancerState(
    var status:BaseState = BaseState.IDLE,
    var error:String ?= null
)