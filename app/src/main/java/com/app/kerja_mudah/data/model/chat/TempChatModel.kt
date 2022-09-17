package com.app.kerja_mudah.data.model.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.google.gson.annotations.SerializedName

@Entity(tableName = ParamsRoom.TEMP_CHAT_TABLE)
data class TempChatModel(
    @SerializedName("status")
    var status:String ?= null,
    @SerializedName("id")
    var id:Int ?= null,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id_local")
    var idLocal:Long ?= null,
    @SerializedName("unique_room_id")
    var uniqueRoomId:String ?= null,
    @SerializedName("send_by")
    var sendBy:SendBy ?= null,
    @SerializedName("chat")
    var chat:String ?= null,
    @SerializedName("media")
    var list:ArrayList<Media> ?= null,
    @SerializedName("replied_to")
    var repliedTo:TempChatModel ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null,
){
    data class Media(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("url")
        var url:String ?= null
    )
    data class SendBy(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("full_name")
        var fullName:String ?= null,
        @SerializedName("photo")
        var photo:String ?= null
    )
}