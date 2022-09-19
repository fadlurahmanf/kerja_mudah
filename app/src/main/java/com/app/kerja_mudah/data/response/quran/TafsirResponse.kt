package com.app.kerja_mudah.data.response.quran

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TafsirResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("surah")
    var surah:Int ?= null,
    @SerializedName("ayat")
    var ayat:Int ?= null,
    @SerializedName("ar")
    var ar:String ?= null,
    @SerializedName("tr")
    var tr:String ?= null,
    @SerializedName("idn")
    var idn:String ?= null,
    @SerializedName("tafsir")
    var tafsir:String ?= null
) : Parcelable
