package com.app.kerja_mudah.data.model.review

import android.os.Parcelable
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewDetailModel(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("star")
    var star:Int ?= null,
    @SerializedName("comment")
    var comment:String ?= null,
    @SerializedName("comment_photo")
    var commentPhoto:List<String> ?= null,
    @SerializedName("from")
    var from: ProfileResponse?= null,
    @SerializedName("freelancer_id")
    var freelancerId:Int ?= null,
    @SerializedName("freelancer")
    var freelancerName:String ?= null,
    @SerializedName("freelancer_photo")
    var freelancerPhoto:String ?= null,
    @SerializedName("freelancer_comment")
    var freelancerComment:String ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null
) : Parcelable
