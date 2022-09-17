package com.app.kerja_mudah.data.response.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LegalResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("legal_name")
    var legalName:String ?= null,
    @SerializedName("html_text")
    var htmlText:String ?= null
) : Parcelable
