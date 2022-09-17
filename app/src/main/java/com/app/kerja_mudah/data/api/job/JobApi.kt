package com.app.kerja_mudah.data.api.job

import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.response.job.JobResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JobApi {
    @POST("api/job/job-category/all")
    fun getAllJobCategory(@Body body: JsonObject): Observable<BaseResponse<List<JobCategoryResponse>>>

    @GET("api/job/all")
    fun getAllJob() : Observable<BaseResponse<List<JobResponse>>>
}