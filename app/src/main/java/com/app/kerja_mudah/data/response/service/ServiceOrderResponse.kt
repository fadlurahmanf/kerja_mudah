package com.app.kerja_mudah.data.response.service

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerProfileResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = ParamsRoom.SERVICE_ORDER_TABLE)
@Parcelize
data class ServiceOrderResponse(
    @PrimaryKey
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("order_id")
    var orderId:String ?= null,
    @SerializedName("service")
    var serviceResponse:ServiceResponse ?= null,
    @SerializedName("buyer")
    var buyer:ProfileResponse ?= null,
    @SerializedName("note_from_buyer")
    var noteFromBuyer:String ?= null,
    @SerializedName("status")
    var status:String ?= null,
    @SerializedName("freelancer")
    var freelancer: FreelancerProfileResponse?= null,
    @SerializedName("promo_code_id")
    var promoCodeId:Int ?= null,
    @SerializedName("promo_code")
    var promoCode:String ?= null,
    @SerializedName("final_price")
    var finalPrice:Double ?= null,
) : Parcelable {}
