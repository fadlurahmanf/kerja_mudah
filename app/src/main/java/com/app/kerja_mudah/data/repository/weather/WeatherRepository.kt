package com.app.kerja_mudah.data.repository.weather

import android.content.Context
import com.app.kerja_mudah.base.BasePreference
import com.app.kerja_mudah.core.constant.ParamsKeySP
import com.app.kerja_mudah.data.response.weather.ProvinceWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    var context: Context
) : BasePreference(context) {

    var provinceSelected: String? = null
        get() {
            return getString(ParamsKeySP.PROVINCE_SELECTED)
        }
        set(value) {
            if (value == null) {
                clearData(ParamsKeySP.PROVINCE_SELECTED)
                field = null
            } else {
                saveString(ParamsKeySP.PROVINCE_SELECTED, value)
                field = value
            }
        }

    var citySelected: String? = null
        get() {
            return getString(ParamsKeySP.CITY_SELECTED)
        }
        set(value) {
            if (value == null) {
                clearData(ParamsKeySP.CITY_SELECTED)
                field = null
            } else {
                saveString(ParamsKeySP.CITY_SELECTED, value)
                field = value
            }
        }

    var weatherResponse:ProvinceWeatherResponse? = null
        get() {
            return getData(ParamsKeySP.PROVINCE_RESPONSE, ProvinceWeatherResponse::class.java)
        }
        set(value) {
            if (value == null) {
                clearData(ParamsKeySP.PROVINCE_RESPONSE)
                field = null
            } else {
                saveData(ParamsKeySP.PROVINCE_RESPONSE, value)
                field = value
            }
        }
}