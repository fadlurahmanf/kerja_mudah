package com.app.kerja_mudah.data.model.freelancer

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.model.auth.ProfileModel
import com.app.kerja_mudah.data.model.service.ServiceModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = ParamsRoom.FREELANCER_TABLE)
data class FreelancerModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int ?= null,
    var freelancerName:String ?= null,
    var profile: ProfileModel?= null,
    var review: Review ?= null,
    var highlightText:String ?= null,
    var highlightPhoto:ArrayList<String> ?= null,
    var highlightVideo:ArrayList<String> ?= null,
    var service: Service ?= null,
    var createdAt: String ?= null,
    var updatedAt:String ?= null
) : Parcelable {
    @Parcelize
    data class Review(
        var totalReview:Int ?= null,
        var totalStar:Int ?= null,
        var data:List<Detail> ?= null
    ) : Parcelable {
        @Parcelize
        data class Detail(
            var id:Int ?= null,
            var star:Int ?= null,
            var comment:String ?= null,
            var commentPhoto:List<String> ?= null,
            var from: ProfileModel ?= null,
            var createdAt:String ?= null
        ) : Parcelable
    }
    @Parcelize
    data class Service(
        var totalService:Int ?= null,
        var data:ArrayList<ServiceModel> ?= null
    ) : Parcelable
}
