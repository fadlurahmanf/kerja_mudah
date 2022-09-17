package com.app.kerja_mudah.data.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileModel(
    var id:Int ?= null,
    var name:String ?= null,
    var email:String ?= null,
    var isUser:Boolean ?= true,
    var isFreelancer:Boolean ?= null,
    var photo:String ?= null
) : Parcelable
