package com.app.kerja_mudah.ui.job.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.job.JobEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class JobDetailViewModel @Inject constructor(
    var jobEntity: JobEntity
) : BaseViewModel() {
    private var stateData = JobDetailState()

    private var _state:MutableLiveData<JobDetailState> = MutableLiveData<JobDetailState>()
    val state get() : LiveData<JobDetailState> = _state

    fun getDetailJob(jobId:Int){
        stateData.getDetailJobState = BaseState.LOADING
        _state.value = stateData
        disposable().add(jobEntity.getDetailJob(jobId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.getDetailJobState = BaseState.SUCCESS
                        stateData.detailJob = it.data
                        _state.value = stateData
                    }else{
                        stateData.getDetailJobState = BaseState.FAILED
                        stateData.errorGetDetailJob = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.getDetailJobState = BaseState.FAILED
                    stateData.errorGetDetailJob = it.message
                    _state.value = stateData
                    stateData.getDetailJobState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getDetailJobState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun getListJobByCategoryId(jobCategoryId:Int){
        stateData.getListJobByCategoryIdState = BaseState.LOADING
        _state.value = stateData
        disposable().add(jobEntity.getListJobByCategoryId(jobCategoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.getListJobByCategoryIdState = BaseState.SUCCESS
                        stateData.listJobByCategoryId = it.data
                        _state.value = stateData
                    }else{
                        stateData.getListJobByCategoryIdState = BaseState.FAILED
                        stateData.errorGetListJobByCategoryId = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.getListJobByCategoryIdState = BaseState.FAILED
                    stateData.errorGetListJobByCategoryId = it.message
                    _state.value = stateData
                    stateData.getListJobByCategoryIdState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getListJobByCategoryIdState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }
}