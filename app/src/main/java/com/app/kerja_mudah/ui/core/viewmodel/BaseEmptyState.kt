package com.app.kerja_mudah.ui.core.viewmodel

import com.app.kerja_mudah.base.BaseState

data class BaseEmptyState(
    var activateEmailStatus:BaseState = BaseState.IDLE,
    var errorActivateEmail:String? = null
)
