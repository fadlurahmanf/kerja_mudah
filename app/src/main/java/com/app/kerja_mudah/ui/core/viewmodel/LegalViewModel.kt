package com.app.kerja_mudah.ui.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.core.CoreEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LegalViewModel @Inject constructor(
    var coreEntity: CoreEntity
): BaseViewModel() {
    private var stateData = LegalState()

    private val _state = MutableLiveData<LegalState>()
    val state:LiveData<LegalState> = _state

    fun getLegalById(id:Int){
        stateData.state = BaseState.LOADING
        _state.value = stateData
        disposable().add(coreEntity.getLegal(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.state = BaseState.SUCCESS
                        stateData.htmlText = it.data?.htmlText
                        _state.value = stateData
                    }else{
                        stateData.state = BaseState.FAILED
                        stateData.errorText = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.state = BaseState.FAILED
                    stateData.errorText = it.message
                    _state.value = stateData
                    stateData.state = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.state = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}