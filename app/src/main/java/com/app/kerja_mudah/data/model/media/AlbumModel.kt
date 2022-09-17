package com.app.kerja_mudah.data.model.media

import android.os.Parcelable
import com.app.kerja_mudah.data.model.MediaModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumModel(
    @SerializedName("bucket_name")
    var bucketName:String ?= null,
    @SerializedName("bucket_id")
    var bucketId:Long ?= null,
    @SerializedName("medias")
    var medias:ArrayList<MediaModel> ?= null
) : Parcelable
