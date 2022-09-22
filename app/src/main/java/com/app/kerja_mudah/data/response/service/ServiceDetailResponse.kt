package com.app.kerja_mudah.data.response.service

import android.os.Parcelable
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceDetailResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("highlight_photo")
    var highlightPhoto:ArrayList<String> ?= null,
    @SerializedName("title")
    var title:String ?= null,
    @SerializedName("definition")
    var definition:String ?= null,
    @SerializedName("price")
    var price:Long ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null,
    @SerializedName("review")
    var review:Review ?= null,
    @SerializedName("freelancer")
    var freelancer: FreelancerResponse?= null
) : Parcelable{
    @Parcelize
    data class Review(
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
        @SerializedName("total_review")
        var totalReview:Int ?= null,
        @SerializedName("total_star")
        var totalStar:Int ?= null,
        @SerializedName("data")
        var data:ArrayList<Detail> ?= null
    ) : Parcelable{
        @Parcelize
        data class Detail(
            @SerializedName("id")
            var id:Int ?= null,
            @SerializedName("star")
            var star:Int ?= null,
            @SerializedName("comment")
            var comment:String ?= null,
            @SerializedName("comment_photo")
            var commentPhoto:ArrayList<String> ?= null,
            @SerializedName("from")
            var from: ProfileResponse?= null,
            @SerializedName("created_at")
            var createdAt:String ?= null
        ) : Parcelable
    }
}