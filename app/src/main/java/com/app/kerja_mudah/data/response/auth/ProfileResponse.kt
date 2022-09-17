package com.app.kerja_mudah.data.response.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("email")
    var email:String ?= null,
    @SerializedName("photo")
    var photo:String ?= null,
    @SerializedName("full_name")
    var fullName:String ?= null,
    @SerializedName("is_user")
    var isUser:Boolean ?= null,
    @SerializedName("is_freelancer")
    var isFreelancer:Boolean ?= null,
    @SerializedName("is_worker")
    var isWorker:String ?= null
) : Parcelable
