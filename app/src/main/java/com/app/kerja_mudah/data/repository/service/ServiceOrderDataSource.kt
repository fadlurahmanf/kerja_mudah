package com.app.kerja_mudah.data.repository.service

import android.content.Context
import android.util.Log
import com.app.kerja_mudah.data.entity.service.ServiceEntity
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import com.app.kerja_mudah.data.room.service.ServiceOrderRoomRepository
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class ServiceOrderDataSource @Inject constructor(
    var context: Context,
    var serviceEntity: ServiceEntity,
    var serviceOrderRoomRepository: ServiceOrderRoomRepository
) {
    companion object{
        val TAG = ServiceOrderDataSource::class.java.simpleName
    }

    fun getServiceOrder(): Observable<BaseResponse<ArrayList<ServiceOrderResponse>>> {
        return Observable.zip(
            getServiceOrderAPI(),
            getServiceOrderLocal(),
            BiFunction { t1, t2 ->
                if (t1.code != 200 || t1.message != "success"){
                    throw IOException(t1.message)
                }
                t1.apply {
                    for (element in 0 until t2.size - 1){
                        if (this.data?.firstOrNull { it.id == t2[element].id } == null){
                            this.data?.add(t2[element])
                        }
                    }
                }
                BaseResponse<ArrayList<ServiceOrderResponse>>(
                    code = t1.code,
                    message = t1.message,
                    data = t1.data
                )
            }
        ).subscribeOn(Schedulers.io())
            .doOnNext {

            }
    }

    fun getServiceOrderAPI(): Observable<BaseResponse<ArrayList<ServiceOrderResponse>>> {
        return serviceEntity.getMyServiceOrder()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                if (it.code == 200 && it.data?.isNotEmpty() == true){
                    insertServiceOrderToLocal(it.data?: arrayListOf())
                }
                Log.d(TAG, "onNext getServiceOrderAPI")
            }
    }

    fun getServiceOrderLocal():Observable<ArrayList<ServiceOrderResponse>> {
        return Observable.just(serviceOrderRoomRepository)
            .subscribeOn(Schedulers.io())
            .map {
                ArrayList(it.getAll() ?: arrayListOf())
            }
    }

    fun insertServiceOrderToLocal(list: ArrayList<ServiceOrderResponse>): Observable<ServiceOrderRoomRepository> {
        return Observable.just(serviceOrderRoomRepository)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                it.insertAll(list)
            }
    }


}