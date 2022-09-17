package com.app.kerja_mudah.data.repository.auth

import android.content.Context
import com.app.kerja_mudah.base.BasePreference
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.data.response.auth.LoginResponse
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    context: Context
) : BasePreference(context) {

    var accessToken: String? = null
        get() {
            field = getString(ParamsKeySP.ACCESS_TOKEN)
            return "Bearer $field"
        }
        set(value) {
            if (value == null) {
                field = null
                clearData(ParamsKeySP.ACCESS_TOKEN)
            } else {
                saveString(ParamsKeySP.ACCESS_TOKEN, value)
                field = value
            }
        }

    var myProfile: ProfileResponse? = null
        get() {
            field = getData(ParamsKeySP.MY_PROFILE, ProfileResponse::class.java)
            return field
        }
        set(value) {
            if (value == null) {
                field = null
                clearData(ParamsKeySP.MY_PROFILE)
            } else {
                saveData(ParamsKeySP.MY_PROFILE, value)
                field = myProfile
            }
        }

    val isLoggedIn: Boolean
        get() = accessToken != null && myProfile != null

    var totalUnreadOrderService:Int ?= null
    get() {
        field = getInt(ParamsKeySP.TOTAL_UNREAD_ORDER_SERVICE)
        return field
    }set(value) {
        if (value == null){
            field = null
            clearData(ParamsKeySP.TOTAL_UNREAD_ORDER_SERVICE)
        }else{
            saveInt(ParamsKeySP.TOTAL_UNREAD_ORDER_SERVICE, value)
            field = value
        }
    }
}