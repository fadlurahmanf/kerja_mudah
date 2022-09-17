package com.app.kerja_mudah.data.model.core

import com.app.kerja_mudah.data.response.core.FaqResponse

data class FaqModel(
    var faqCategory:String ?= null,
    var faqResponse:FaqResponse ?= null
)
