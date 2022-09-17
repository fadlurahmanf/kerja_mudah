package com.app.kerja_mudah.data.response.auth

import com.google.gson.annotations.SerializedName

data class PartnerUserResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("name")
    var name:String ?= null,
    @SerializedName("photo")
    var photo:String ?= null
)
