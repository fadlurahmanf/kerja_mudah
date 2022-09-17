package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.model.auth.ProfileModel
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.Gson

class AuthConverter {
    @TypeConverter
    fun fromProfileResponseToString(profile: ProfileResponse?):String?{
        return if (profile != null){
            Gson().toJson(profile)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToProfileResponse(value:String?): ProfileResponse?{
        return if (value != null){
            Gson().fromJson(value, ProfileResponse::class.java)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromProfileModelToString(profile: ProfileModel?):String?{
        return if (profile != null){
            Gson().toJson(profile)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToProfileModel(value: String?): ProfileModel?{
        return if (value != null){
            Gson().fromJson(value, ProfileModel::class.java)
        }else{
            null
        }
    }
}