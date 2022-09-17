package com.app.kerja_mudah.data.model.service

import android.os.Parcelable
import com.app.kerja_mudah.data.model.auth.ProfileModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceModel(
    var id:Int ?= null,
    var highlightPhoto:ArrayList<String> ?= null,
    var title:String ?= null,
    var definition:String ?= null,
    var price:Long ?= null,
    var createdAt:String ?= null,
    var updatedAt:String ?= null,
    var review:Review ?= null,
    var freelancer: FreelancerModel?= null
) : Parcelable {
    @Parcelize
    data class Review(
        var totalReview:Int ?= null,
        var totalStar:Int ?= null,
        var data:ArrayList<Detail> ?= null
    ) : Parcelable {
        @Parcelize
        data class Detail(
            var id:Int ?= null,
            var star:Int ?= null,
            var comment:String ?= null,
            var commentPhoto:ArrayList<String> ?= null,
            var from: ProfileModel?= null,
            var createdAt:String ?= null
        ) : Parcelable
    }
}
