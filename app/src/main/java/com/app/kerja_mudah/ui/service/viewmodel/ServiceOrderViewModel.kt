package com.app.kerja_mudah.ui.service.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.repository.service.ServiceOrderDataSource
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ServiceOrderViewModel @Inject constructor(
    var serviceOrderDataSource: ServiceOrderDataSource
) : BaseViewModel(){
    companion object{
        val TAG = ServiceOrderViewModel::class.java.simpleName
    }

    val stateData = ServiceOrderState()

    private var _state = MutableLiveData<ServiceOrderState>()
    val state:LiveData<ServiceOrderState> = _state

    fun getAllServiceOrder(){
        stateData.serviceOrderState = BaseState.LOADING
        _state.value = stateData
        disposable().add(serviceOrderDataSource.getServiceOrder()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.serviceOrderState = BaseState.SUCCESS
                        stateData.resultServiceOrder = it.data?: arrayListOf<ServiceOrderResponse>()
                        _state.value = stateData
                    }
                },
                {
                    stateData.serviceOrderState = BaseState.FAILED
                    stateData.errorServiceOrder = it.message
                    _state.value = stateData

                    stateData.serviceOrderState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.serviceOrderState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}
