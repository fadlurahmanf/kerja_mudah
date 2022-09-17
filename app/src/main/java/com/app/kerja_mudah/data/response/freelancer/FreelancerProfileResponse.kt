package com.app.kerja_mudah.data.response.freelancer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FreelancerProfileResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("freelancer_name")
    var freelancerName:String ?= null,
    @SerializedName("free")
    var photo:String ?= null
) : Parcelable
