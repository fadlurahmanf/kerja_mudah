package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.model.chat.TempChatModel
import com.google.gson.Gson
import org.json.JSONArray

class TempChatModelConverter {
    @TypeConverter
    fun fromTempChatToString(tempChatModel: TempChatModel?):String?{
        return if (tempChatModel != null){
            Gson().toJson(tempChatModel)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromStringToTempChat(value: String?): TempChatModel?{
        return if (value != null){
            Gson().fromJson(value, TempChatModel::class.java)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromSendByToString(sendBy: TempChatModel.SendBy?):String?{
        return if (sendBy != null){
            Gson().toJson(sendBy)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromStringToSendBy(value: String?): TempChatModel.SendBy?{
        return if (value != null){
            Gson().fromJson(value, TempChatModel.SendBy::class.java)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromArrayListMediaToString(list:ArrayList<TempChatModel.Media>?):String?{
        return if (list != null){
            Gson().toJson(list)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToArrayListMedia(value:String?):ArrayList<TempChatModel.Media>?{
        return if (value != null){
            val list:ArrayList<TempChatModel.Media> = arrayListOf()
            val jsonArray = JSONArray(value)
            for (i in 0 until jsonArray.length()){
                val row = jsonArray.getJSONObject(i)
                list.add(Gson().fromJson(row.toString(), TempChatModel.Media::class.java))
            }
            list
        }else{
            null
        }
    }

    @TypeConverter
    fun fromMediaToString(media: TempChatModel.Media?):String?{
        return if (media != null){
            Gson().toJson(media)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromStringToMedia(value: String?): TempChatModel.Media?{
        return if (value != null){
            Gson().fromJson(value, TempChatModel.Media::class.java)
        } else {
            null
        }
    }
}