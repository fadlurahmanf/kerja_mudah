package com.app.kerja_mudah.ui.freelancer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.data.mapper.FreelancerMapper
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.freelancer.PagingFreelancerResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class FreelancerPagingSource (
    var freelancerEntity: FreelancerEntity,
    var mapper: FreelancerMapper
) : RxPagingSource<Int, FreelancerModel>() {
    companion object{
        const val DEFAULT_PAGE_INDEX = 1
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FreelancerModel>> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val result = freelancerEntity.getAllPagingFreelancer(page)
        return result.subscribeOn(Schedulers.io())
            .map { it ->
                if (it.code == 200 && it.message == "success"){
                    println("Masuk mapping: ${it.code} dan ${it.message}")
                    toLoadResult(it.data, page)
                }else{
                    println("Masuk toLoadError: ${it.code} dan ${it.message}")
                    LoadResult.Error(Throwable(it.message))
                }
            }.onErrorReturn { error ->
                println("Masuk onErrorReturn ${error.message}")
                LoadResult.Error(error)
            }
    }

    private fun toLoadResult(data:PagingFreelancerResponse?, page:Int):LoadResult<Int, FreelancerModel>{
        return LoadResult.Page(
            data = ArrayList(data?.data?.map {
                mapper.fromResponseToModel(it)
            } ?: arrayListOf()),
            prevKey = if(page == 1) null else page - 1,
            nextKey = if(page == (data?.totalPages?:1)) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, FreelancerModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}