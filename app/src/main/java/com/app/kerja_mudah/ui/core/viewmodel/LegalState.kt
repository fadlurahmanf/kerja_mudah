package com.app.kerja_mudah.ui.core.viewmodel

import com.app.kerja_mudah.base.BaseState

data class LegalState(
    var state:BaseState = BaseState.IDLE,
    var htmlText:String ?= null,
    var errorText:String ?= null
)
