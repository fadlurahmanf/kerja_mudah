package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerProfileResponse
import com.google.gson.Gson

class FreelanceConverter {
    @TypeConverter
    fun fromReviewToString(review: FreelancerModel.Review?):String?{
        return if (review != null){
            Gson().toJson(review)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToReview(value: String?): FreelancerModel.Review?{
        return if (value != null){
            return Gson().fromJson(value, FreelancerModel.Review::class.java)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromServiceToString(service: FreelancerModel.Service?):String?{
        return if (service != null){
            Gson().toJson(service)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToService(value: String?): FreelancerModel.Service?{
        return if (value != null){
            return Gson().fromJson(value, FreelancerModel.Service::class.java)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromFreelancerProfileResponseToString(profile: FreelancerProfileResponse):String?{
        return if (profile != null){
            Gson().toJson(profile)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToFreelancerProfileResponse(value: String?): FreelancerProfileResponse?{
        return if (value != null){
            return Gson().fromJson(value, FreelancerProfileResponse::class.java)
        }else{
            null
        }
    }
}