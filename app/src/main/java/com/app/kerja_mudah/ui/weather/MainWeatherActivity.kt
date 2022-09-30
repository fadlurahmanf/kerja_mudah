package com.app.kerja_mudah.ui.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.repository.weather.WeatherRepository
import com.app.kerja_mudah.databinding.ActivityMainWeatherBinding
import com.app.kerja_mudah.di.component.WeatherComponent
import com.app.kerja_mudah.ui.weather.viewmodel.MainWeatherViewModel
import javax.inject.Inject

class MainWeatherActivity : BaseActivity<ActivityMainWeatherBinding>(ActivityMainWeatherBinding::inflate) {
    private var citySelected:String ?= null
    private var provinceSelected:String ?= null

    override fun initSetup() {
        citySelected = repository.citySelected
        provinceSelected = repository.provinceSelected
//        viewModel.getProvinceWeather("JawaBarat")

        if (citySelected == null){
            val intent = Intent(this, ChooseLocationWeatherActivity::class.java)
            startActivity(intent)
            return
        }

    }

    @Inject
    lateinit var repository:WeatherRepository

    @Inject
    lateinit var viewModel:MainWeatherViewModel

    private lateinit var component: WeatherComponent
    override fun inject() {
        component = appComponent.weatherComponent().create()
        component.inject(this)
    }

}