package com.app.kerja_mudah.data.api.service

import androidx.annotation.Nullable
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.service.ServicePromoCodeResponse
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ServiceApi {
    @GET("api/v1/service/detail/{id}")
    fun getServiceDetail(
        @Path("id") id:Int
    ) : Observable<BaseResponse<ServiceDetailResponse>>

    @POST("api/v1/service/order")
    fun orderService(
        @Header("Authorization") authorization:String,
        @Body body:JsonObject
    ) : Observable<BaseResponse<Nullable>>

    @POST("api/v1/service/apply-promo")
    fun applyPromoCode(
        @Header("Authorization") authorization:String,
        @Body body:JsonObject
    ) : Observable<BaseResponse<ServicePromoCodeResponse>>

    @GET("api/v1/service/order/my")
    fun getMyServiceOrder(
        @Header("Authorization") authorization:String,
    ) : Observable<BaseResponse<ArrayList<ServiceOrderResponse>>>
}