package com.app.kerja_mudah.ui.home.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.core.fcm.FcmUtils
import com.app.kerja_mudah.data.entity.auth.AuthEntity
import com.app.kerja_mudah.data.entity.core.CoreEntity
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.data.entity.job.JobEntity
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.response.core.AdsResponse
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.job.JobResponse
import com.app.kerja_mudah.data.room.job.JobRoomRepository
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    var jobEntity: JobEntity,
    var jobRoomRepository: JobRoomRepository,
    var freelancerEntity: FreelancerEntity,
    var coreEntity: CoreEntity,
    var authEntity: AuthEntity,
    var authRepository: AuthRepository
) : BaseViewModel() {
    companion object{
        val TAG = HomeViewModel::class.java.simpleName
    }
    private var homeStateData: HomeState = HomeState()

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    fun getAllJobCategory() {
        val body = JsonObject()
        body.addProperty("flag", "all")
        homeStateData.jobCategoryState = BaseState.LOADING
        _homeState.postValue(homeStateData)
        disposable().add(jobEntity.getAllJobCategory(body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success") {
                        viewModelScope.launch {
                            jobRoomRepository.deleteAll()
                            jobRoomRepository.insertAll(it.data ?: listOf())
                        }
                        homeStateData.jobCategoryState = BaseState.SUCCESS
                        homeStateData.dataJobCategory = it.data
                        homeStateData.errorMessageJobCategory = null
                        _homeState.postValue(homeStateData)
                    } else {
                        homeStateData.jobCategoryState = BaseState.FAILED
                        homeStateData.errorMessageJobCategory = it.message
                        _homeState.postValue(homeStateData)
                    }
                },
                {
                    homeStateData.jobCategoryState = BaseState.FAILED
                    homeStateData.errorMessageJobCategory = it.message
                    _homeState.postValue(homeStateData)
                },
                {}
            ))
    }

    fun getAllAdsBanner(){
        homeStateData.adsBannerState = BaseState.LOADING
        _homeState.value = homeStateData
        disposable().add(coreEntity.getAds("home_tab")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        homeStateData.adsBannerState = BaseState.SUCCESS
                        homeStateData.dataAdsBanner = it.data
                        _homeState.value = homeStateData
                    }else{
                        homeStateData.adsBannerState = BaseState.FAILED
                        homeStateData.errorMessageAdsBanner = it.message
                        _homeState.value = homeStateData
                    }
                },
                {
                    homeStateData.adsBannerState = BaseState.FAILED
                    homeStateData.errorMessageAdsBanner = it.message
                    _homeState.value = homeStateData
                },
                {}
            ))
    }

    fun getAllReelFreelancer(){
        homeStateData.reelsFreelancerState = BaseState.LOADING
        _homeState.value = homeStateData
        disposable().add(freelancerEntity.getListReelByFreelancer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        homeStateData.reelsFreelancerState = BaseState.SUCCESS
                        homeStateData.listReelFreelancer = it.data ?: arrayListOf()
                        _homeState.value = homeStateData
                    }else{
                        homeStateData.reelsFreelancerState = BaseState.FAILED
                        homeStateData.errorReelsFreelancer = it.message
                        _homeState.value = homeStateData
                    }
                },
                {
                    homeStateData.reelsFreelancerState = BaseState.FAILED
                    homeStateData.errorReelsFreelancer = it.message
                    _homeState.value = homeStateData
                    homeStateData.reelsFreelancerState = BaseState.IDLE
                    _homeState.value = homeStateData
                },
                {
                    homeStateData.reelsFreelancerState = BaseState.IDLE
                    _homeState.value = homeStateData
                }
            ))
    }

    fun getAllFreelancer() {
        homeStateData.allFreelancerState = BaseState.LOADING
        _homeState.value = homeStateData
        disposable().add(freelancerEntity.getAllFreelancer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success") {
                        homeStateData.allFreelancerState = BaseState.SUCCESS
                        homeStateData.dataAllFreelancer = it.data
                        _homeState.value = homeStateData
                    } else {
                        homeStateData.allFreelancerState = BaseState.FAILED
                        homeStateData.errorMessageAllFreelancer = it.message
                        _homeState.value = homeStateData
                    }
                },
                {
                    homeStateData.allFreelancerState = BaseState.FAILED
                    homeStateData.errorMessageAllFreelancer = it.message
                    _homeState.value = homeStateData
                },
                {}
            ))
    }

    fun getListJob() {
        homeStateData.allJobState = BaseState.LOADING
        _homeState.value = homeStateData
        disposable().add(jobEntity.getAllJob()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success") {
                        homeStateData.allJobState = BaseState.SUCCESS
                        homeStateData.dataAllJob = it.data
                        _homeState.value = homeStateData
                    } else {
                        homeStateData.allJobState = BaseState.FAILED
                        homeStateData.errorAllJob = it.message
                        _homeState.value = homeStateData
                    }
                },
                {
                    homeStateData.allJobState = BaseState.FAILED
                    homeStateData.errorAllJob = it.message
                    _homeState.value = homeStateData
                },
                {}
            ))
    }


    fun updateToken(){
        val fcmService = FcmUtils()
        val body = JsonObject()
        fcmService.getNewToken().addOnSuccessListener {
            Log.i(TAG, "updateToken : $it")
            body.addProperty("fcm_token", it)
            disposable().add(authEntity.updateFcmToken(
                authorization = authRepository.accessToken?:"",
                body = body
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {}, {}, {}
                ))
        }
    }
}