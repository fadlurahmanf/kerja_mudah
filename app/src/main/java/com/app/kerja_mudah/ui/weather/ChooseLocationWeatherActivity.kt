package com.app.kerja_mudah.ui.weather

import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityChooseLocationWeatherBinding
import com.app.kerja_mudah.di.component.WeatherComponent
import com.app.kerja_mudah.ui.weather.viewmodel.MainWeatherViewModel
import javax.inject.Inject

class ChooseLocationWeatherActivity : BaseActivity<ActivityChooseLocationWeatherBinding>(ActivityChooseLocationWeatherBinding::inflate) {
    override fun initSetup() {

    }

    @Inject
    lateinit var viewModel: MainWeatherViewModel

    private lateinit var component: WeatherComponent
    override fun inject() {
        component = appComponent.weatherComponent().create()
        component.inject(this)
    }

}