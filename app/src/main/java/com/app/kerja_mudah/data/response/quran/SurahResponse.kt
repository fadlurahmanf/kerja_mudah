package com.app.kerja_mudah.data.response.quran

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurahResponse(
    @SerializedName("nomor")
    var nomor:Int ?= null,
    @SerializedName("nama")
    var nama:String ?= null,
    @SerializedName("nama_latin")
    var namaLatin:String ?= null,
    @SerializedName("jumlah_ayat")
    var jumlahAyat:String ?= null,
    @SerializedName("tempat_turun")
    var tempatTurun:String ?= null,
    @SerializedName("arti")
    var arti:String ?= null,
    @SerializedName("deskripsi")
    var deskripsi:String ?= null,
    @SerializedName("audio")
    var audio:String ?= null,
    @SerializedName("ayat")
    var ayat:ArrayList<AyahResponse> ?= null
) : Parcelable
