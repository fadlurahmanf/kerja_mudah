package com.app.kerja_mudah.ui.freelancer.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.data.mapper.FreelancerMapper
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerRemoteKeyModel
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.freelancer.PagingFreelancerResponse
import com.app.kerja_mudah.data.room.freelancer.FreelancerRemoteKeyRepository
import com.app.kerja_mudah.data.room.freelancer.FreelancerRoomRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FreelancerRemoteMediator @Inject constructor(
    var freelancerEntity: FreelancerEntity,
    var freelancerRoomRepository: FreelancerRoomRepository,
    var freelancerRemoteKeyRepository: FreelancerRemoteKeyRepository,
    var mapper:FreelancerMapper
):RxRemoteMediator<Int, FreelancerModel>() {

    companion object{
        val TAG = FreelancerRemoteMediator::class.java.simpleName
    }
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, FreelancerModel>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map { type ->
                when(type){
                    LoadType.REFRESH -> {
                        Log.d(TAG, "map loadType: $type")
                        val remoteKey = getRefreshKey(state)
                        remoteKey?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        Log.d(TAG, "map loadType: $type")
                        val remoteKey = getRemoteKeyForFirstTime(state)
                        remoteKey?.prevKey ?: -1
                    }
                    LoadType.APPEND -> {
                        Log.d(TAG, "map loadType: $type")
                        val remoteKey = getRemoteKeyForLastTime(state)
                        remoteKey?.nextKey ?: -1
                    }
                }
            }.flatMap { page ->
                Log.d(TAG, "flatMap: page $page")
                if (page == -1){
                    Single.just(MediatorResult.Error(Throwable("Invalid Page")))
                }else{
                    freelancerEntity.getAllPagingFreelancer(page = page)
                        .map {
                            Log.d(TAG, "result ${it.code} | ${it.message}")
                            insertToDb(page, loadType, it.data, it)
                        }
                        .map<MediatorResult> {
                            if (it.code == 200 && it.message == "success"){
                                MediatorResult.Success(endOfPaginationReached = it.data?.currentPage == it.data?.totalPages)
                            }else{
                                MediatorResult.Error(Throwable(it.message))
                            }
                        }
                        .onErrorReturn {
                            Log.d(TAG, "onErrorReturn: ${it.message}")
                            MediatorResult.Error(it)
                        }
                }
            }.onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertToDb(
        page: Int,
        loadType: LoadType,
        pagingData: PagingFreelancerResponse?,
        result: BaseResponse<PagingFreelancerResponse>
    ): BaseResponse<PagingFreelancerResponse> {
        Log.d(TAG, "insertToDb: $page | $loadType | ${pagingData != null}")
        if (pagingData == null){ return result }
        try {
            if (loadType == LoadType.REFRESH){
                freelancerRemoteKeyRepository.clear()
                freelancerRoomRepository.clear()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (pagingData.currentPage == pagingData.totalPages) null else page + 1
            val keys = ArrayList(pagingData.data?.map {
                FreelancerRemoteKeyModel(freelancerId = it.id, prevKey = prevKey, nextKey = nextKey)
            } ?: listOf())
            val models = ArrayList(pagingData.data?.map {
                mapper.fromResponseToModel(it)
            }?: listOf())
            freelancerRemoteKeyRepository.insertAll(keys)
            freelancerRoomRepository.insertAll(models)
        }catch (e:Exception){
            Log.d(TAG, "insertToDb: ${e.message}")
        }

        return result
    }

    private fun getRemoteKeyForLastTime(state: PagingState<Int, FreelancerModel>):FreelancerRemoteKeyModel?{
        Log.d(TAG, "getRemoteKeyForFirstTime: ${state.pages.isNotEmpty()} | ${state.pages.firstOrNull()?.data?.isNotEmpty()} | ${state.pages.firstOrNull()?.data?.firstOrNull()?.id != null}")
        if (state.pages.isEmpty()){
            return null
        }

        if (state.pages.last().data.isEmpty()){
            return null
        }

        if (state.pages.last().data.last().id == null){
            return null
        }

        return freelancerRemoteKeyRepository.getRemoteKeyByFreelancer(state.pages.last().data.last().id!!)
    }

    private fun getRemoteKeyForFirstTime(state: PagingState<Int, FreelancerModel>):FreelancerRemoteKeyModel?{
        Log.d(TAG, "getRemoteKeyForFirstTime: ${state.pages.isNotEmpty()} | ${state.pages.firstOrNull()?.data?.isNotEmpty()} | ${state.pages.firstOrNull()?.data?.firstOrNull()?.id != null}")
        if (state.pages.isEmpty()){
            return null
        }

        if (state.pages.first().data.isEmpty()){
            return null
        }

        if (state.pages.first().data.first().id == null){
            return null
        }

        return freelancerRemoteKeyRepository.getRemoteKeyByFreelancer(state.pages.first().data.first().id!!)
    }

    private fun getRefreshKey(state: PagingState<Int, FreelancerModel>): FreelancerRemoteKeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                freelancerRemoteKeyRepository.getRemoteKeyByFreelancer(id)
            }
        }
    }
}