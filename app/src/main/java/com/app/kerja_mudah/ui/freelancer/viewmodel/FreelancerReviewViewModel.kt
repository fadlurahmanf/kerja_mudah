package com.app.kerja_mudah.ui.freelancer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.ui.freelancer.state.FreelancerReviewState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FreelancerReviewViewModel @Inject constructor(
    var freelancerEntity: FreelancerEntity
) : BaseViewModel() {
    private var stateData = FreelancerReviewState()

    private var _state:MutableLiveData<FreelancerReviewState> = MutableLiveData<FreelancerReviewState>(stateData)
    val state:LiveData<FreelancerReviewState> get() = _state

    fun getAllReviewByFreelancerId(id:Int){
        stateData.state = BaseState.LOADING
        _state.postValue(stateData)
        disposable().add(freelancerEntity.getAllReviewByFreelancerId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.state = BaseState.SUCCESS
                        stateData.data = it.data
                        _state.postValue(stateData)
                    }else{
                        stateData.state = BaseState.FAILED
                        stateData.error = it.message
                        _state.postValue(stateData)
                    }
                },
                {
                    stateData.state = BaseState.FAILED
                    stateData.error = it.message
                    _state.postValue(stateData)
                },
                {}
            ))
    }
}