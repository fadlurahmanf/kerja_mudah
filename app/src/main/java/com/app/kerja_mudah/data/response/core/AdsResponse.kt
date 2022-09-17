package com.app.kerja_mudah.data.response.core

import com.google.gson.annotations.SerializedName

data class AdsResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("flag")
    var flag:String ?= null,
    @SerializedName("destination_url")
    var destinationUrl:String ?= null,
    @SerializedName("thumbnail")
    var thumbnail:String ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null
)
