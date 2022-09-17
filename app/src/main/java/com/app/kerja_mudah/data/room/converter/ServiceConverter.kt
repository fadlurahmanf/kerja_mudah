package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.response.service.ServiceResponse
import com.google.gson.Gson

class ServiceConverter {

    @TypeConverter
    fun fromServiceResponseToString(service: ServiceResponse):String?{
        return if (service != null){
            Gson().toJson(service)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToServiceResponse(value: String?): ServiceResponse?{
        return if (value != null){
            return Gson().fromJson(value, ServiceResponse::class.java)
        }else{
            null
        }
    }
}