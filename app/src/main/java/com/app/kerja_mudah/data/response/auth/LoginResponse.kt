package com.app.kerja_mudah.data.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    var accessToken:String ?= null,
    @SerializedName("profile")
    var profile:ProfileResponse ?= null
)
