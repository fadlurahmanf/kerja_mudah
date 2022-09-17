package com.app.kerja_mudah.data.response.freelancer

import com.google.gson.annotations.SerializedName

data class PagingFreelancerResponse(
    @SerializedName("total_pages")
    var totalPages: Int ?= null,
    @SerializedName("current_page")
    var currentPage: Int ?= null,
    @SerializedName("total_data")
    var totalData: Int ?= null,
    @SerializedName("data")
    var data:ArrayList<FreelancerResponse> ?= null
)
