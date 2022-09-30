package com.app.kerja_mudah.ui.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.weather.WeatherEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainWeatherViewModel @Inject constructor(
    var weatherEntity: WeatherEntity
) : BaseViewModel() {

    private var stateData = MainWeatherState()

    private var _state:MutableLiveData<MainWeatherState> = MutableLiveData<MainWeatherState>()
    val state get():LiveData<MainWeatherState> = _state

    fun getProvinceWeather(province:String){
        stateData.getWeatherProvince = BaseState.LOADING
        _state.value = stateData
        disposable().add(weatherEntity.getProvinceWeather(province)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stateData.getWeatherProvince = BaseState.SUCCESS
                    stateData.resultWeatherProvince = it
                    _state.value = stateData
                },
                {
                    stateData.getWeatherProvince = BaseState.FAILED
                    stateData.errorGetWeather = it.message
                    _state.value = stateData
                    stateData.getWeatherProvince = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getWeatherProvince = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}