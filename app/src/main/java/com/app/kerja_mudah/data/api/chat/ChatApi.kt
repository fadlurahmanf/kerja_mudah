package com.app.kerja_mudah.data.api.chat

import androidx.annotation.Nullable
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ChatApi {
    @GET("api/v1/chat/room/{opponent_id}")
    fun getDetailRoomChat(
        @Path("opponent_id") opponentId:Int
    ) : Observable<BaseResponse<ChatRoomResponse>>

    @Multipart
    @POST("api/v1/chat/room/{opponent_id}/send")
    fun sendChat(
        @Path("opponent_id") opponentId:Int,
        @Part body:List<MultipartBody.Part>
    ) : Observable<BaseResponse<Nullable>>


    @GET("api/chat/room-chat/all")
    fun getAllRoomChat(
        @Header("Authorization") token:String
    ) : Observable<BaseResponse<ArrayList<ChatRoomResponse>>>
}