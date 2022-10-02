package com.app.kerja_mudah.ui.weather

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.constant.UpdateWeatherLocation
import com.app.kerja_mudah.core.utilities.RxBus
import com.app.kerja_mudah.data.repository.weather.WeatherRepository
import com.app.kerja_mudah.data.response.weather.Area
import com.app.kerja_mudah.data.response.weather.Parameter
import com.app.kerja_mudah.data.response.weather.ProvinceWeatherResponse
import com.app.kerja_mudah.data.response.weather.TimeRange
import com.app.kerja_mudah.databinding.ActivityMainWeatherBinding
import com.app.kerja_mudah.di.component.WeatherComponent
import com.app.kerja_mudah.ui.weather.adapter.WeatherAdapter
import com.app.kerja_mudah.ui.weather.viewmodel.MainWeatherViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainWeatherActivity : BaseActivity<ActivityMainWeatherBinding>(ActivityMainWeatherBinding::inflate) {
    private var citySelected:String? = null
    private var provinceSelected:String? = null
    private var weatherResponse:ProvinceWeatherResponse? = null
    private var area:Area? = null
    private var temperature:Parameter? = null
    private var weather:Parameter? = null
    private var isCelcius:Boolean = true


    override fun initSetup() {
        citySelected = repository.citySelected
        provinceSelected = repository.provinceSelected
        weatherResponse = repository.weatherResponse
        setLocation()
        initAction()
        initAdapter()
        if (citySelected == null || provinceSelected == null){
            val intent = Intent(this, ChooseLocationWeatherActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            return
        }
        setArea()
        setTemperature()
        setWeather()
        initObserver()
        initBus()
        viewModel.getProvinceWeather(provinceSelected?.replace(" ", "")?:"")
    }

    private fun initBus() {
        RxBus.listen(UpdateWeatherLocation::class.java).subscribe {
            citySelected = repository.citySelected
            provinceSelected = repository.provinceSelected
            weatherResponse = repository.weatherResponse
            setLocation()
            setArea()
            setTemperature()
            setWeather()
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if(it.getWeatherProvince == BaseState.SUCCESS){
                weatherResponse = it.resultWeatherProvince
                setArea()
                setTemperature()
                setWeather()
            }
        }
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            finish()
        }
        binding?.llCity?.setOnClickListener {
            val intent = Intent(this, ChooseLocationWeatherActivity::class.java)
            startActivity(intent)
        }
        binding?.ivSwapTemperature?.setOnClickListener {
            if (isCelcius){
                isCelcius = false
                binding?.tvTemperatureCelcius?.visibility = View.GONE
                binding?.tvTemperatureFarenheit?.visibility = View.VISIBLE
            }else{
                isCelcius = true
                binding?.tvTemperatureCelcius?.visibility = View.VISIBLE
                binding?.tvTemperatureFarenheit?.visibility = View.GONE
            }
        }
    }

    private lateinit var adapter:WeatherAdapter
    private var listWeather:ArrayList<TimeRange> = arrayListOf()
    private var listTemperature:ArrayList<TimeRange> = arrayListOf()
    private fun initAdapter() {
        adapter = WeatherAdapter()
        adapter.setListWeather(listWeather)
        adapter.setListTemperatuer(listTemperature)
        binding?.rv?.adapter = adapter
    }

    private fun setLocation() {
        binding?.tvCity?.text = citySelected?:"City"
    }

    private fun setArea(){
        area = weatherResponse?.forecast?.area?.firstOrNull {
            it.city?.equals(citySelected, true) == true && it.province?.equals(provinceSelected, true) == true
        }
    }

    private fun setTemperature(){
        temperature = area?.parameter?.firstOrNull {
            it.id.equals("t", ignoreCase = false)
        }
        val currentTemp = selectTimeRange(temperature?.timerange?: listOf())
        binding?.tvTemperatureCelcius?.text = getString(R.string.temperature_celcius, "${currentTemp?.value?.firstOrNull {
            it.unit.equals("C", ignoreCase = false)
        }?.value}")
        binding?.tvTemperatureFarenheit?.text = getString(R.string.temperature_farenheit, "${currentTemp?.value?.firstOrNull {
            it.unit.equals("F", ignoreCase = false)
        }?.value}")
        listTemperature.clear()
        listTemperature.addAll(ArrayList(temperature?.timerange?: listOf()))
        adapter.setListTemperatuer(listTemperature)
    }

    private fun setWeather(){
        weather = area?.parameter?.firstOrNull {
            it.id.equals("weather", ignoreCase = false)
        }
        val currentWeather = selectTimeRange(weather?.timerange?: listOf())
        binding?.ivSky?.setImageDrawable(ContextCompat.getDrawable(this, drawableResWeather(currentWeather?.value?.firstOrNull {
            it.unit.equals("icon", ignoreCase = false)
        }?.value?.toIntOrNull()?:0)))
        binding?.tvWeatherType?.text = weatherType(currentWeather?.value?.firstOrNull {
            it.unit.equals("icon", ignoreCase = false)
        }?.value?.toIntOrNull()?:0)

        listWeather.clear()
        listWeather.addAll(ArrayList(weather?.timerange?: listOf()))
        adapter.setListWeather(listWeather)
    }

    private fun setHumidity(){

    }

    private fun weatherType(code: Int):String{
        if (code == 0){
            return "Clear Skies"
        }else if (code in 1..2){
            return "Partly Cloudy"
        }else if (code in 3..4){
            return "Mostly Cloudy"
        }else if (code in 5..59){
            return "Cloudy"
        }else if (code in 60..62){
            return "Light Rain"
        }else if (code in 63..94){
            return "Heavy Rain"
        }else{
            return "Severe Thunderstorm"
        }
    }

    private fun drawableResWeather(code:Int):Int{
        if (code == 0){
            return R.drawable.il_sunny
        }else if (code in 1..2){
            return R.drawable.il_partly_cloudy
        }else if (code in 3..4){
            return R.drawable.il_mostly_cloudy
        }else if (code in 5..59){
            return R.drawable.il_cloudy
        }else if (code in 60..62){
            return R.drawable.il_light_rain
        }else if (code in 63..94){
            return R.drawable.il_heavy_rain
        }else{
            return R.drawable.il_storm
        }
    }

    private fun selectTimeRange(list:List<TimeRange>):TimeRange?{
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        var index = 0
        for (i in list.indices){
            val date: Date = sdf.parse(list[i].datetime?:"") ?: continue
            if (date.after(currentDate) && i != 0){
                index = i-1
                break
            }
        }
        return if (list.isNotEmpty())
            list[index] else null
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