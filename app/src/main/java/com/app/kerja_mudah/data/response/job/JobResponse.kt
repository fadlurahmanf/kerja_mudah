package com.app.kerja_mudah.data.response.job

import com.app.kerja_mudah.data.response.auth.PartnerUserResponse
import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("total_salary")
    var totalSalary:Long ?= null,
    @SerializedName("salary_per_hour")
    var salaryPerHour:Long ?= null,
    @SerializedName("category")
    var category:JobCategoryResponse ?= null,
    @SerializedName("partner_user")
    var partnerUser:PartnerUserResponse ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null
)
