package com.app.kerja_mudah.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityMainWeatherBinding
import com.app.kerja_mudah.di.component.WeatherComponent
import com.app.kerja_mudah.ui.weather.viewmodel.MainWeatherViewModel
import javax.inject.Inject

class MainWeatherActivity : BaseActivity<ActivityMainWeatherBinding>(ActivityMainWeatherBinding::inflate) {
    override fun initSetup() {
        viewModel.getProvinceWeather("Aceh")
    }

    @Inject
    lateinit var viewModel:MainWeatherViewModel

    private lateinit var component: WeatherComponent
    override fun inject() {
        component = appComponent.weatherComponent().create()
        component.inject(this)
    }

}