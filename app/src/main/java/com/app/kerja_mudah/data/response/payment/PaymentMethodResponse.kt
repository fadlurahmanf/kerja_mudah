package com.app.kerja_mudah.data.response.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentMethodResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("name")
    var name:String ?= null,
    @SerializedName("logo")
    var logo:String ?= null,
    @SerializedName("type")
    var type:String ?= null,
    @SerializedName("how_to_pay_url")
    var howToPayUrl:String ?= null,
    @SerializedName("active")
    var active:Boolean ?= null,
    @SerializedName("visible")
    var visible:Boolean ?= null
) : Parcelable