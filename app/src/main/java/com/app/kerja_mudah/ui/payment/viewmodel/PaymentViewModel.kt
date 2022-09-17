package com.app.kerja_mudah.ui.payment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.payment.PaymentEntity
import com.app.kerja_mudah.data.mapper.payment.PaymentMethodMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PaymentViewModel @Inject constructor(
    var paymentEntity: PaymentEntity,
    var paymentMethodMapper: PaymentMethodMapper
) : BaseViewModel() {
    var stateData = PaymentState()

    private var _state = MutableLiveData<PaymentState>()
    val state:LiveData<PaymentState> = _state

    fun getAllPaymentMethod(){
        stateData.getAllPaymentMethodState = BaseState.LOADING
        _state.value = stateData
        disposable().add(paymentEntity.getAllPaymentMethod()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.getAllPaymentMethodState = BaseState.SUCCESS
                        stateData.listPaymentMethod = paymentMethodMapper.mapFromListResponseToListModel(it.data?: arrayListOf())
                        _state.value = stateData
                    }else{
                        stateData.getAllPaymentMethodState = BaseState.FAILED
                        stateData.errorGetAllPaymentMethod = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.getAllPaymentMethodState = BaseState.FAILED
                    stateData.errorGetAllPaymentMethod = it.message
                    _state.value = stateData
                    stateData.getAllPaymentMethodState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getAllPaymentMethodState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}