package com.app.kerja_mudah.ui.weather

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.view.ContextThemeWrapper
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.repository.weather.WeatherRepository
import com.app.kerja_mudah.data.response.weather.Area
import com.app.kerja_mudah.data.response.weather.ProvinceWeatherResponse
import com.app.kerja_mudah.databinding.ActivityChooseLocationWeatherBinding
import com.app.kerja_mudah.di.component.WeatherComponent
import com.app.kerja_mudah.ui.weather.viewmodel.MainWeatherViewModel
import javax.inject.Inject

class ChooseLocationWeatherActivity : BaseActivity<ActivityChooseLocationWeatherBinding>(ActivityChooseLocationWeatherBinding::inflate) {
    private var citySelected:String ?= null
    private var provinceSelected:String ?= null
    private var provinceWeatherResponse:ProvinceWeatherResponse? = null

    @Inject
    lateinit var repository: WeatherRepository

    override fun initSetup() {
        citySelected = repository.citySelected
        provinceSelected = repository.provinceSelected
        initView()
        initAction()
        initAdapter()
        initObserver()
    }

    private lateinit var adapter: WeatherCityAdapter
    private var listArea:ArrayList<Area> = arrayListOf()
    private fun initAdapter() {
        adapter = WeatherCityAdapter()
        adapter.setCallback(object : WeatherCityAdapter.CallBack{
            override fun onClicked(area: Area) {
                finish()
            }
        })
        adapter.setList(listArea)
        binding?.rv?.adapter = adapter
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getWeatherProvince == BaseState.SUCCESS){
                binding?.pb?.visibility = View.GONE
                binding?.llEmpty?.visibility = View.GONE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.VISIBLE
                provinceWeatherResponse = it.resultWeatherProvince
                listArea.clear()
                listArea.addAll(ArrayList(it.resultWeatherProvince?.forecast?.area?: listOf()))
                adapter.setList(listArea)
            }else if (it.getWeatherProvince == BaseState.FAILED){
                binding?.pb?.visibility = View.GONE
                binding?.llEmpty?.visibility = View.GONE
                binding?.tvError?.visibility = View.VISIBLE
                binding?.rv?.visibility = View.GONE
            }else if (it.getWeatherProvince == BaseState.LOADING){
                binding?.pb?.visibility = View.VISIBLE
                binding?.llEmpty?.visibility = View.GONE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.GONE
            }
        }
    }

    private fun initView() {
        setEmptyView()
    }

    private fun setEmptyView(){
        binding?.pb?.visibility = View.GONE
        binding?.llEmpty?.visibility = View.VISIBLE
        binding?.tvError?.visibility = View.GONE
        binding?.rv?.visibility = View.GONE
    }

    private var provinceMenu:PopupMenu ?= null

    private fun initAction() {
        binding?.llProvince?.setOnClickListener {
            if (provinceMenu != null){
                provinceMenu?.dismiss()
                provinceMenu = null
                return@setOnClickListener
            }
            provinceMenu = PopupMenu(this, binding!!.llProvince)
            provinceMenu!!.menuInflater?.inflate(R.menu.menu_province, provinceMenu!!.menu)
            provinceMenu!!.setOnMenuItemClickListener(menuCallback)
            provinceMenu!!.setOnDismissListener(menuDismiss)
            provinceMenu!!.show()
        }
    }

    private var menuCallback = PopupMenu.OnMenuItemClickListener {
        provinceSelected = it.title.toString()
        binding?.tvProvinceName?.text = it.title
        if (citySelected != it.title){
            viewModel.getProvinceWeather(it.title.toString().replace(" ", ""))
        }
        true
    }

    private var menuDismiss = PopupMenu.OnDismissListener {
        provinceMenu?.dismiss()
        provinceMenu = null
    }

    @Inject
    lateinit var viewModel: MainWeatherViewModel

    private lateinit var component: WeatherComponent
    override fun inject() {
        component = appComponent.weatherComponent().create()
        component.inject(this)
    }

}