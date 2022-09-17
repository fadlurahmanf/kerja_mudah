package com.app.kerja_mudah.ui.auth.state

import com.app.kerja_mudah.base.BaseState

data class RegisterState(
    var registerState:BaseState ?= BaseState.IDLE,
    var errorRegister:String ?= null
)
