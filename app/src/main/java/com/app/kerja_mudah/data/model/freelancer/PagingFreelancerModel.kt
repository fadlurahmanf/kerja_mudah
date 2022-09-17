package com.app.kerja_mudah.data.model.freelancer

import com.google.gson.annotations.SerializedName

data class PagingFreelancerModel(
    @SerializedName("total_pages")
    var totalPages: Int ?= null,
    @SerializedName("current_page")
    var currentPage: Int ?= null,
    @SerializedName("total_data")
    var totalData: Int ?= null,
    @SerializedName("data")
    var data:ArrayList<FreelancerModel> ?= null
)
