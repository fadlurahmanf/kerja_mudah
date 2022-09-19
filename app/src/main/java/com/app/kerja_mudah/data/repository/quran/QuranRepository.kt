package com.app.kerja_mudah.data.repository.quran

import android.content.Context
import com.app.kerja_mudah.base.BasePreference
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.data.model.quran.QuranFontSize
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuranRepository @Inject constructor(
    var context: Context
) : BasePreference(context) {

    var fontSize:QuranFontSize = QuranFontSize()
    get() {
        field = getData(ParamsKeySP.QURAN_FONT_SIZE, QuranFontSize::class.java) ?: QuranFontSize()
        return field
    }set(value) {
        saveData(ParamsKeySP.QURAN_FONT_SIZE, value)
        field = value
    }
}