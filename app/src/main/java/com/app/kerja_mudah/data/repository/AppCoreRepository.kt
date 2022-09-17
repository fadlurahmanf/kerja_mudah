package com.app.kerja_mudah.data.repository

import android.content.Context
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.base.BasePreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCoreRepository @Inject constructor(
    context: Context
) : BasePreference(context) {

    var codeLanguage : String ?= null
    get() {
        field = getString(ParamsKeySP.CODE_LANGUAGE_APP)
        return field
    }set(value) {
        if (value!=null){
            saveString(ParamsKeySP.CODE_LANGUAGE_APP, value)
            field = value
        }
    }
}