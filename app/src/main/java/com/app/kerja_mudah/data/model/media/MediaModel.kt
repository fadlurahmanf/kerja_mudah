package com.app.kerja_mudah.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaModel(
    @SerializedName("id")
    var id:Long ?= null,
    @SerializedName("path")
    var path:String ?= null,
    @SerializedName("bucket_name")
    var bucketName:String ?= null, /** Album Name */
    @SerializedName("bucket_id")
    var bucketId:Long ?= null, /** Album Id */
    @SerializedName("date_added")
    var dateAdded:String ?= null,
    @SerializedName("type")
    var type:MediaModelType ?= MediaModelType.UNKNOWN,
) : Parcelable

enum class MediaModelType{
    VIDEO, PHOTO, UNKNOWN
}
