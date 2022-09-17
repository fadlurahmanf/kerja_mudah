package com.app.kerja_mudah.data.model.freelancer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.kerja_mudah.core.constant.ParamsRoom

@Entity(tableName = ParamsRoom.FREELANCER_REMOTE_KEY_TABLE)
data class FreelancerRemoteKeyModel(
    @PrimaryKey
    var freelancerId:Int ?= null,
    var prevKey:Int ?= null,
    var nextKey:Int ?= null
)
