package com.app.kerja_mudah.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.auth.AuthEntity
import com.app.kerja_mudah.ui.auth.state.RegisterState
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    var authEntity: AuthEntity
) : BaseViewModel() {

    private var stateData = RegisterState()

    private var _state = MutableLiveData<RegisterState>(stateData)
    val state get() : LiveData<RegisterState> = _state

    fun register(email:String, fullName:String, password:String){
        stateData.registerState = BaseState.LOADING
        _state.value = stateData
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("full_name", fullName)
        body.addProperty("password", password)
        disposable().add(authEntity.register(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.registerState = BaseState.SUCCESS
                        _state.value = stateData
                    }else{
                        stateData.registerState = BaseState.FAILED
                        stateData.errorRegister = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.registerState = BaseState.FAILED
                    stateData.errorRegister = it.message
                    _state.value = stateData
                },
                {
                    stateData.registerState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}