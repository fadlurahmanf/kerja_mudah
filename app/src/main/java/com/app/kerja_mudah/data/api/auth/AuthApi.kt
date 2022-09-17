package com.app.kerja_mudah.data.api.auth

import androidx.annotation.Nullable
import com.app.kerja_mudah.data.response.auth.LoginResponse
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("api/v1/auth/login")
    fun login(@Body body:JsonObject) : Observable<BaseResponse<LoginResponse>>

    @POST("api/v1/auth/google-login")
    fun googleLogin(@Body body: JsonObject) : Observable<BaseResponse<LoginResponse>>

    @POST("api/v1/auth/update-fcm-token")
    fun updateToken(
        @Header("Authorization") auth:String,
        @Body body: JsonObject
    ) : Observable<BaseResponse<Nullable>>

    @POST("api/v1/auth/register")
    fun regis(@Body body: JsonObject) : Observable<BaseResponse<Nullable>>

    @GET("api/v1/auth/confirmation-registration/{token_user}")
    fun activateAccount(
        @Path("token_user") tokenUser:String,
        @Query("token") token:String
    ) : Observable<BaseResponse<Nullable>>
}