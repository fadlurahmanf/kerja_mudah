package com.app.kerja_mudah.data.response.job

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobDetailResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("name")
    var name:String ?= null,
    @SerializedName("description")
    var description:String ?= null,
    @SerializedName("salary")
    var salary:Long ?= null,
    @SerializedName("full_day")
    var fullDay:Boolean ?= null,
    @SerializedName("work_hour")
    var workHour:Int ?= null,
    @SerializedName("work_time")
    var workTime:String ?= null,
    @SerializedName("city")
    var city:String ?= null,
    @SerializedName("latitude")
    var latitude:String ?= null,
    @SerializedName("longitude")
    var longitude:String ?= null,
    @SerializedName("company")
    var company:Company ?= null,
    @SerializedName("job_desk")
    var jobDesk:ArrayList<String> ?= null,
    @SerializedName("responsibility")
    var responsibility:ArrayList<String> ?= null,
    @SerializedName("status")
    var status:Boolean ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null
) : Parcelable{
    @Parcelize
    data class Company(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("name")
        var name:String ?= null,
        @SerializedName("photo")
        var photo:String ?= null,
        @SerializedName("description")
        var description:String ?= null
    ) : Parcelable
}
