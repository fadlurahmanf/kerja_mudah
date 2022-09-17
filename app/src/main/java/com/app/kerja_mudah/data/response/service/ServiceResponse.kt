package com.app.kerja_mudah.data.response.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("highlight_photo")
    var highlightPhoto:ArrayList<String> ?= null,
    @SerializedName("highlight_video")
    var highlightVideo:ArrayList<String> ?= null,
    @SerializedName("title")
    var title:String ?= null,
    @SerializedName("definition")
    var definition:String ?= null,
    @SerializedName("price")
    var price:Double ?= null,
) : Parcelable
