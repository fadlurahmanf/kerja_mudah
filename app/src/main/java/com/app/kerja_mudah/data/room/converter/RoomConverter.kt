package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.model.auth.ProfileModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.response.auth.ProfileResponse
import com.google.gson.Gson
import org.json.JSONArray

class RoomConverter {
    @TypeConverter
    fun fromArrayListStringToString(list:ArrayList<String>?):String?{
        return if (list != null){
            return Gson().toJson(list)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToArrayListString(value: String?):ArrayList<String>?{
        return if (value != null){
            val list:ArrayList<String> = arrayListOf()
            val jsonArray = JSONArray(value)
            for (i in 0 until jsonArray.length()){
                val row = jsonArray.getString(i)
                list.add(row)
            }
            list
        }else{
            null
        }
    }
}