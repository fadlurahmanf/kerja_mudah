package com.app.kerja_mudah.ui.quran.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.quran.SurahResponse

data class QuranState(
    var getListSurahState:BaseState = BaseState.IDLE,
    var listSurah:ArrayList<SurahResponse> ?= null,
    var errorListSurah:String ?= null,

    var getDetailSurah:BaseState = BaseState.IDLE,
    var detailSurah:SurahResponse ?= null,
    var playAudio:Boolean = false,
    var errorDetailSurah:String ?= null
)
