package com.app.kerja_mudah.data.response.job

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.google.gson.annotations.SerializedName

@Entity(tableName = ParamsRoom.JOB_CATEGORY_TABLE)
data class JobCategoryResponse(
    @PrimaryKey
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("flag")
    var flag:String ?= null,
    @SerializedName("title")
    var title:String ?= null,
    @SerializedName("type")
    var type:String ?= null,
    @SerializedName("definition")
    var definition:String ?= null
)
