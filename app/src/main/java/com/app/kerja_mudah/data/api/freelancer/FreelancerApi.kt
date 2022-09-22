package com.app.kerja_mudah.data.api.freelancer

import androidx.annotation.Nullable
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerReviewResponse
import com.app.kerja_mudah.data.response.freelancer.PagingFreelancerResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface FreelancerApi {
    @GET("api/v1/freelancer/list")
    fun getAllFreelancer() : Observable<BaseResponse<List<FreelancerResponse>>>

    @GET("api/v1/freelancer/all")
    fun getAllPagingFreelancer(
        @Query("page") page:Int? = null
    ) : Single<BaseResponse<PagingFreelancerResponse>>

    @GET("api/v1/freelancer/detail/{id}")
    fun getDetailFreelancer(
        @Path("id") id:Int
    ) : Observable<BaseResponse<FreelancerResponse>>

    @GET("api/v1/freelancer/{freelancerId}/review/all")
    fun getAllReviewByFreelancerId(
        @Path("freelancerId") id:Int
    ) : Observable<BaseResponse<FreelancerReviewResponse>>

    @Multipart
    @POST("api/v1/freelancer/register")
    fun registerAsFreelancer(
        @Header("Authorization") bearerToken:String,
        @Part part:List<MultipartBody.Part>
    ) : Observable<BaseResponse<Nullable>>

    @GET("api/v1/freelancer/reels")
    fun getListReelsByFreelancer(
        @Header("Authorization") token:String
    ) : Observable<BaseResponse<ArrayList<FreelancerReelResponse>>>
}