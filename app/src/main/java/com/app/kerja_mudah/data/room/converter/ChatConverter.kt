package com.app.kerja_mudah.data.room.converter

import androidx.room.TypeConverter
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.google.gson.Gson
import org.json.JSONArray

class ChatConverter {

    @TypeConverter
    fun fromInfoToString(info:ChatRoomResponse.Info?):String?{
        return if (info != null){
            Gson().toJson(info)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToInfo(value:String?):ChatRoomResponse.Info?{
        return if (value != null){
            Gson().fromJson(value, ChatRoomResponse.Info::class.java)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromArrayListChatResponseToString(list:ArrayList<ChatResponse>?):String?{
        return if (list != null){
            Gson().toJson(list)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToArrayListChatResponse(value:String?):ArrayList<ChatResponse>?{
        return if (value != null){
            val list:ArrayList<ChatResponse> = arrayListOf()
            val jsonArray = JSONArray(value)
            for (i in 0 until jsonArray.length()){
                val row = jsonArray.getJSONObject(i)
                list.add(Gson().fromJson(row.toString(), ChatResponse::class.java))
            }
            list
        }else{
            null
        }
    }

    @TypeConverter
    fun fromArrayListMediaToString(list:ArrayList<ChatResponse.Media>?):String?{
        return if (list != null){
            Gson().toJson(list)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToArrayListMedia(value:String?):ArrayList<ChatResponse.Media>?{
        return if (value != null){
            val list:ArrayList<ChatResponse.Media> = arrayListOf()
            val jsonArray = JSONArray(value)
            for (i in 0 until jsonArray.length()){
                val row = jsonArray.getJSONObject(i)
                list.add(Gson().fromJson(row.toString(), ChatResponse.Media::class.java))
            }
            list
        }else{
            null
        }
    }

    @TypeConverter
    fun fromChatResponseToString(chatResponse: ChatResponse?):String?{
        return if (chatResponse != null){
            Gson().toJson(chatResponse)
        }else{
            null
        }
    }

    @TypeConverter
    fun fromStringToChatResponse(value:String?):ChatResponse?{
        return if (value != null){
            Gson().fromJson(value, ChatResponse::class.java)
        } else {
            null
        }
    }
}