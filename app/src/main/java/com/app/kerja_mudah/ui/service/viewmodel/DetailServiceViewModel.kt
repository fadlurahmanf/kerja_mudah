package com.app.kerja_mudah.ui.service.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.service.ServiceEntity
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailServiceViewModel @Inject constructor(
    var serviceEntity: ServiceEntity
) : BaseViewModel() {
    private var stateData:DetailServiceState = DetailServiceState()

    private var _state:MutableLiveData<DetailServiceState> = MutableLiveData<DetailServiceState>()
    val state get() : LiveData<DetailServiceState> = _state

    fun getDetailService(id:Int){
        stateData.getDetailState = BaseState.LOADING
        _state.postValue(stateData)
        disposable().add(serviceEntity.getServiceDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stateData.getDetailState = BaseState.SUCCESS
                    stateData.serviceDetailData = it.data
                    _state.value = stateData
                },
                {
                    stateData.getDetailState = BaseState.FAILED
                    stateData.errorGetDetail = it.message
                    _state.value = stateData
                },
                {
                    stateData.getDetailState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun orderService(id:Int){
        stateData.orderState = BaseState.LOADING
        _state.value = stateData
        val body = JsonObject()
        body.addProperty("id", id)
        disposable().addAll(serviceEntity.orderService(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.orderState = BaseState.SUCCESS
                        _state.value = stateData
                    }else{
                        stateData.orderState = BaseState.FAILED
                        stateData.errorOrder = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.orderState = BaseState.FAILED
                    stateData.errorOrder = it.message
                    _state.value = stateData
                },
                {
                    stateData.orderState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}