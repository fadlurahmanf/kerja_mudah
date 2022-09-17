package com.app.kerja_mudah.data.response.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = ParamsRoom.CHAT_ROOM_TABLE)
data class ChatRoomResponse(
    @PrimaryKey
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("unique_room_id")
    var uniqueRoomId:String ?= null,
    @SerializedName("info")
    var info:Info ?= null,
    @SerializedName("last_chat")
    var lastChat:ChatResponse ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null,
    @SerializedName("chats")
    var chats:ArrayList<ChatResponse> ?= null
){
    data class Info(
        @SerializedName("opponent")
        var opponent:Opponent ?= null
    ){
        data class Opponent(
            @SerializedName("name")
            var name:String ?= null,
            @SerializedName("email")
            var email:String ?= null,
            @SerializedName("photo")
            var photo:String ?= null
        )
    }
}
