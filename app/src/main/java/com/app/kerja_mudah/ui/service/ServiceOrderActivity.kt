package com.app.kerja_mudah.ui.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import com.app.kerja_mudah.databinding.ActivityServiceOrderBinding
import com.app.kerja_mudah.di.component.ServiceComponent
import com.app.kerja_mudah.ui.service.adapter.ServiceOrderAdapter
import com.app.kerja_mudah.ui.service.viewmodel.ServiceOrderState
import com.app.kerja_mudah.ui.service.viewmodel.ServiceOrderViewModel
import javax.inject.Inject

class ServiceOrderActivity : BaseActivity<ActivityServiceOrderBinding>(ActivityServiceOrderBinding::inflate) {
    override fun initSetup() {
        initAdapter()
        viewModel.getAllServiceOrder()
        initObserver()
    }

    private lateinit var adapter:ServiceOrderAdapter
    private var list:ArrayList<ServiceOrderResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = ServiceOrderAdapter()
        adapter.setList(list)
        binding?.rvServiceOrder?.adapter = adapter
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.serviceOrderState == BaseState.LOADING){
                binding?.llError?.visibility = View.GONE
                binding?.llLoading?.visibility = View.VISIBLE
                binding?.rvServiceOrder?.visibility = View.GONE
            }else if (it.serviceOrderState == BaseState.FAILED){
                binding?.llError?.visibility = View.VISIBLE
                binding?.llLoading?.visibility = View.GONE
                binding?.rvServiceOrder?.visibility = View.GONE
            }else if (it.serviceOrderState == BaseState.SUCCESS){
                binding?.llError?.visibility = View.GONE
                binding?.llLoading?.visibility = View.GONE
                binding?.rvServiceOrder?.visibility = View.VISIBLE

                list.clear()
                list.addAll(it.resultServiceOrder?: arrayListOf())
                adapter.setList(list)
            }
        }
    }

    @Inject
    lateinit var viewModel:ServiceOrderViewModel


    private lateinit var component: ServiceComponent
    override fun inject() {
        component = appComponent.serviceComponent().create()
        component.inject(this)
    }

}