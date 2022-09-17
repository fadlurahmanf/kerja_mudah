package com.app.kerja_mudah.data.response.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaqResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("category")
    var category:String ?= null,
    @SerializedName("question")
    var question:String ?= null,
    @SerializedName("answer")
    var answer:String ?= null
) : Parcelable
