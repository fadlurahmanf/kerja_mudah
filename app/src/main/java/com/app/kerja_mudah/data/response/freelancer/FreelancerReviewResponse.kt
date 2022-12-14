package com.app.kerja_mudah.data.response.freelancer

import android.os.Parcelable
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FreelancerReviewResponse(
    @SerializedName("total_review")
    var totalReview:Int ?= null,
    @SerializedName("total_star")
    var totalStar:Int ?= null,
    @SerializedName("one_star")
    var oneStar:Int ?= null,
    @SerializedName("two_star")
    var twoStar:Int ?= null,
    @SerializedName("three_star")
    var threeStar:Int ?= null,
    @SerializedName("four_star")
    var fourStar:Int ?= null,
    @SerializedName("five_star")
    var fiveStar:Int ?= null,
    @SerializedName("data")
    var data:List<Detail> ?= null
) : Parcelable {
    @Parcelize
    data class Detail(
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
        @SerializedName("freelancer")
        var freelancer: Freelancer?= null,
        @SerializedName("freelancer_comment")
        var freelancerComment:String ?= null,
        @SerializedName("created_at")
        var createdAt:String ?= null,
        @SerializedName("updated_at")
        var updatedAt:String ?= null,
    ) : Parcelable{
        @Parcelize
        data class Freelancer(
            @SerializedName("id")
            var id:Int ?= null,
            @SerializedName("freelancer_name")
            var freelancerName:String ?= null,
            @SerializedName("photo")
            var photo:String ?= null
        ) : Parcelable
    }
}