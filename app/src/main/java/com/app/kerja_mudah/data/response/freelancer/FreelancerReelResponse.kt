package com.app.kerja_mudah.data.response.freelancer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FreelancerReelResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("url")
    var url:String ?= null,
    @SerializedName("description")
    var description:String ?= null,
    @SerializedName("freelancer")
    var freelancer:FreelancerProfileResponse ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null
) : Parcelable
