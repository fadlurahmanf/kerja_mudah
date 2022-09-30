package com.app.kerja_mudah.ui.weather.viewmodel

import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.weather.WeatherEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainWeatherViewModel @Inject constructor(
    var weatherEntity: WeatherEntity
) : BaseViewModel() {

    fun getProvinceWeather(province:String){
        disposable().add(weatherEntity.getProvinceWeather(province)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    println("masuk ${it.forecast?.issue?.timestamp}")
                    println("masuk ${it.forecast?.domain}")
                },
                {
                    println("masuk salah ${it.message}")
                },
                {}
            ))
    }
}