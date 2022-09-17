package com.app.kerja_mudah.ui.auth.state

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.auth.LoginResponse
import com.app.kerja_mudah.data.response.core.BaseResponse

data class LoginState(
    var state:BaseState ?= BaseState.IDLE,
    var data:BaseResponse<LoginResponse> ?= null,
    var errorMessage: String ?= null
)