package com.app.kerja_mudah.ui.freelancer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.room.freelancer.FreelancerRoomRepository
import com.app.kerja_mudah.ui.freelancer.paging.FreelancerPagingSource
import com.app.kerja_mudah.ui.freelancer.paging.FreelancerRemoteMediator
import com.app.kerja_mudah.ui.freelancer.state.DetailFreelancerState
import com.app.kerja_mudah.ui.freelancer.state.PagingFreelancerState
import com.app.kerja_mudah.ui.freelancer.state.RegisterFreelancerState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import java.io.File
import javax.inject.Inject

class FreelancerViewModel @Inject constructor(
    var freelancerEntity: FreelancerEntity,
    var authRepository: AuthRepository,
    var freelancerRoomRepository: FreelancerRoomRepository,
    var freelancerRemoteMediator: FreelancerRemoteMediator
) : BaseViewModel() {
    private var detailFreelancerStateData = DetailFreelancerState()

    private var _detailFreelancerState = MutableLiveData<DetailFreelancerState>(detailFreelancerStateData)
    val detailFreelancerState:LiveData<DetailFreelancerState> get() = _detailFreelancerState

    fun getDetailFreelancer(id:Int){
        detailFreelancerStateData.state = BaseState.LOADING
        _detailFreelancerState.value = detailFreelancerStateData
        disposable().add(freelancerEntity.getDetailFreelancer(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        detailFreelancerStateData.state = BaseState.SUCCESS
                        detailFreelancerStateData.data = it.data
                        _detailFreelancerState.postValue(detailFreelancerStateData)
                    }else{
                        detailFreelancerStateData.state = BaseState.FAILED
                        detailFreelancerStateData.error = it.message
                        _detailFreelancerState.postValue(detailFreelancerStateData)
                    }
                },
                {
                    detailFreelancerStateData.state = BaseState.FAILED
                    detailFreelancerStateData.error = it.message
                    _detailFreelancerState.postValue(detailFreelancerStateData)
                },
                {}
            ))
    }

    private var pagingFreelancerStateData = PagingFreelancerState()

    private var _pagingFreelancerState = MutableLiveData<PagingFreelancerState>(pagingFreelancerStateData)
    val pagingFreelancerState:LiveData<PagingFreelancerState> get() = _pagingFreelancerState

    @OptIn(ExperimentalPagingApi::class)
    fun getAllPagingFreelancer():Flowable<PagingData<FreelancerModel>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = true,
                initialLoadSize = 10,
            ),
            remoteMediator = freelancerRemoteMediator,
            pagingSourceFactory = {
//                FreelancerPagingSource(freelancerEntity)
                freelancerRoomRepository.selectAll()
            }
        ).flowable.cachedIn(viewModelScope)
    }

    private var registerFreelancerStateData = RegisterFreelancerState()

    private var _registerFreelancerState = MutableLiveData<RegisterFreelancerState>(registerFreelancerStateData)
    val registerFreelancerState get():LiveData<RegisterFreelancerState>  = _registerFreelancerState

    fun registerFreelancer(category:String, name:String, description:String, photos:ArrayList<File>, videos:ArrayList<File>, idCard:File, selfieIdCard:File){
        registerFreelancerStateData.status = BaseState.LOADING
        _registerFreelancerState.value = registerFreelancerStateData
        val partIdCard = MultipartBody.Part.create(idCard.asRequestBody("image/*".toMediaTypeOrNull()))
        val partSelfieIdCard = MultipartBody.Part.create(selfieIdCard.asRequestBody("image/*".toMediaTypeOrNull()))

        val builder = MultipartBody.Builder()

        builder.addFormDataPart("freelancer_category", category)
        builder.addFormDataPart("freelancer_name", name)
        builder.addFormDataPart("freelancer_description", description)
        photos.forEach {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            builder.addFormDataPart("photos", it.name, requestBody)
        }

        videos.forEach {
            val requestBody = it.asRequestBody("video/*".toMediaTypeOrNull())
            builder.addFormDataPart("videos", it.name, requestBody)
        }

        builder.addFormDataPart("id_card", idCard.name, partIdCard.body)
        builder.addFormDataPart("selfie_id_card", selfieIdCard.name, partSelfieIdCard.body)


        disposable().add(
            freelancerEntity.registerAsFreelancer(
                authRepository.accessToken?:"",
                builder.build().parts
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.code == 200){
                            registerFreelancerStateData.status = BaseState.SUCCESS
                            _registerFreelancerState.value = registerFreelancerStateData
                        }else{
                            registerFreelancerStateData.error = it.message
                            _registerFreelancerState.value = registerFreelancerStateData
                        }
                    },
                    {
                        registerFreelancerStateData.error = it.message
                        _registerFreelancerState.value = registerFreelancerStateData
                    },
                    {
                        registerFreelancerStateData.status = BaseState.IDLE
                        _registerFreelancerState.value = registerFreelancerStateData
                    }
                )
        )
    }
}