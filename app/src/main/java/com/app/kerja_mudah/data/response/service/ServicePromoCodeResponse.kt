package com.app.kerja_mudah.data.response.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServicePromoCodeResponse(
    @SerializedName("code")
    var code:String ?= null,
    @SerializedName("discount")
    var discount:Int ?= null,
    @SerializedName("discount_price")
    var discountPrice:Double ?= null,
    @SerializedName("initial_price")
    var initialPrice:Double ?= null,
    @SerializedName("final_price")
    var finalPrice:Double ?= null,
) : Parcelable
