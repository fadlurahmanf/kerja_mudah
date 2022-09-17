package com.app.kerja_mudah.ui.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.auth.AuthEntity
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BaseEmptyViewModel @Inject constructor(
    var authEntity: AuthEntity
):BaseViewModel() {
    private val stateData:BaseEmptyState = BaseEmptyState()

    private var _state = MutableLiveData<BaseEmptyState>()
    val state get() : LiveData<BaseEmptyState> = _state

    fun activateEmail(tokenUser:String, token:String){
        stateData.activateEmailStatus = BaseState.LOADING
        _state.value = stateData
        disposable().add(
            authEntity.activateEmail(tokenUser, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.code == 200 && it.message == "success"){
                            stateData.activateEmailStatus = BaseState.SUCCESS
                            _state.value = stateData
                        }else{
                            stateData.activateEmailStatus = BaseState.FAILED
                            stateData.errorActivateEmail = it.message
                            _state.value = stateData
                        }
                    },
                    {
                        stateData.activateEmailStatus = BaseState.FAILED
                        stateData.errorActivateEmail = it.message
                        _state.value = stateData
                    },
                    {
                        stateData.activateEmailStatus = BaseState.IDLE
                        stateData.errorActivateEmail = null
                        _state.value = stateData
                    }
                )
        )
    }
}