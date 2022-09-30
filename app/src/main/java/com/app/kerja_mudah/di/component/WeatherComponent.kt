package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.weather.MainWeatherActivity
import com.app.kerja_mudah.ui.weather.ChooseLocationWeatherActivity
import dagger.Subcomponent

@Subcomponent
interface WeatherComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():WeatherComponent
    }

    fun inject(activity: MainWeatherActivity)
    fun inject(activity: ChooseLocationWeatherActivity)
}