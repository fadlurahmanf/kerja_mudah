package com.app.kerja_mudah.data.response.core

import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.annotations.SerializedName

data class NotificationResponse<T>(
    @SerializedName("data")
    var data:T ?= null,
    @SerializedName("notification")
    var notification: Notification ?= null
){
    data class Chat(
        @SerializedName("opponent_id")
        var opponentId:String ?= null,
        @SerializedName("opponent_full_name")
        var opponentFullName:String ?= null,
        @SerializedName("opponent_photo")
        var opponentPhoto:String ?= null
    )
    data class Notification(
        @SerializedName("body")
        var body: String ?= null,
        @SerializedName("title")
        var title: String ?= null,
        @SerializedName("type")
        var type:String ?= null,
        @SerializedName("id")
        var id:String ?= null,
        @SerializedName("message")
        var message:String ?= null
    )
}
