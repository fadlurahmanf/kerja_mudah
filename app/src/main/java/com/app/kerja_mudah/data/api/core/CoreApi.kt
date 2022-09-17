package com.app.kerja_mudah.data.api.core

import com.app.kerja_mudah.data.response.core.AdsResponse
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.core.LegalResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*


interface CoreApi {
    @GET("api/v1/ads/all")
    fun getAds(@Query("flag") flag:String) : Observable<BaseResponse<List<AdsResponse>>>

    @GET("api/v1/legal/{id}")
    fun getLegal(
        @Path("id") id:Int
    ) : Observable<BaseResponse<LegalResponse>>
}