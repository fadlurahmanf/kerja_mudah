package com.app.kerja_mudah.ui.payment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.service.ServiceEntity
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import com.app.kerja_mudah.data.response.service.ServicePromoCodeResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    var serviceEntity: ServiceEntity
) : BaseViewModel() {
    private var stateData = CheckoutState()

    private var _state:MutableLiveData<CheckoutState> = MutableLiveData<CheckoutState>()
    val state:LiveData<CheckoutState> = _state

    fun orderService(freelancerId:Int, serviceId:Int, promoCode: ServicePromoCodeResponse?, paymentMethod:PaymentMethodResponse){
        stateData.orderServiceState = BaseState.LOADING
        _state.value = stateData
        val jsonObject = JsonObject()
        jsonObject.addProperty("freelancer_id", freelancerId)
        jsonObject.addProperty("service_id", serviceId)
        jsonObject.addProperty("payment_method_id", paymentMethod.id)

        if (promoCode?.code != null){
            jsonObject.addProperty("promo_code", promoCode.code)
        }

        disposable().add(serviceEntity.orderService(jsonObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 201 && it.message == "success"){
                        stateData.orderServiceState = BaseState.SUCCESS
                        _state.value = stateData
                    }else{
                        stateData.orderServiceState = BaseState.FAILED
                        stateData.errorOrderService = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.orderServiceState = BaseState.FAILED
                    stateData.errorOrderService = it.message
                    _state.value = stateData
                    stateData.orderServiceState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.orderServiceState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun applyPromo(code:String, price:Double){
        val body = JsonObject()
        body.addProperty("code", code)
        body.addProperty("price", price)
        stateData.applyPromoState = BaseState.LOADING
        _state.value = stateData
        disposable().add(serviceEntity.applyPromoCode(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.applyPromoState = BaseState.SUCCESS
                        stateData.resultApplyPromo = it.data
                        _state.value = stateData
                    }else{
                        stateData.applyPromoState = BaseState.FAILED
                        stateData.errorApplyPromo = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.applyPromoState = BaseState.FAILED
                    stateData.errorApplyPromo = it.message
                    _state.value = stateData
                    stateData.applyPromoState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.applyPromoState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}