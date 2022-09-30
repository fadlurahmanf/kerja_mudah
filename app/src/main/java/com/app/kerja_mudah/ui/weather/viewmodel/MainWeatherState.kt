package com.app.kerja_mudah.ui.weather.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.weather.ProvinceWeatherResponse

data class MainWeatherState(
    var getWeatherProvince:BaseState = BaseState.IDLE,
    var resultWeatherProvince:ProvinceWeatherResponse? = null,
    var errorGetWeather:String? = null
)
