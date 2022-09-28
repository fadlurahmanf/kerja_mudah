package com.app.kerja_mudah.data.api.job

import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.response.job.JobDetailResponse
import com.app.kerja_mudah.data.response.job.JobResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JobApi {
    @POST("api/job/job-category/all")
    fun getAllJobCategory(@Body body: JsonObject): Observable<BaseResponse<List<JobCategoryResponse>>>

    @GET("api/v1/job/all")
    fun getListJob() : Observable<BaseResponse<List<JobResponse>>>

    @GET("api/v1/job/{id}")
    fun getDetailJob(
        @Path("id") id:Int
    ) : Observable<BaseResponse<JobDetailResponse>>

    @GET("api/v1/job/all/job-category/{id}")
    fun getListJobByCategoryId(
        @Path("id") jobCategoryId:Int
    ) : Observable<BaseResponse<List<JobResponse>>>
}