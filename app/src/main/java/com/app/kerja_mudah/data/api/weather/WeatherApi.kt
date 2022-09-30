package com.app.kerja_mudah.data.api.weather

import com.app.kerja_mudah.data.response.weather.ProvinceWeatherResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {
    @GET("DigitalForecast-{province}.xml")
    fun getWeatherForecastProvince(
        @Path("province") provinceString: String
    ) : Observable<ProvinceWeatherResponse>
}