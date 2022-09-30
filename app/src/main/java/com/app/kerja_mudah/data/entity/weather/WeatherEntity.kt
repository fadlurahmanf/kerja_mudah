package com.app.kerja_mudah.data.entity.weather

import android.content.Context
import com.app.kerja_mudah.base.network.AbstractForecastBMKGNetwork
import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.weather.WeatherApi
import javax.inject.Inject

class WeatherEntity @Inject constructor(
    var context: Context
) : AbstractForecastBMKGNetwork<WeatherApi>() {
    override fun getApi(): Class<WeatherApi> {
        return WeatherApi::class.java
    }

    fun getProvinceWeather(province:String) = networkService(30).getWeatherForecastProvince(province)
}