package com.app.kerja_mudah.data.mapper

import com.app.kerja_mudah.core.extension.formatDate
import com.app.kerja_mudah.core.extension.toCalendar2
import com.app.kerja_mudah.data.model.chat.ChatModel
import com.app.kerja_mudah.data.model.chat.TempChatModel
import com.app.kerja_mudah.data.response.chat.ChatResponse
import javax.inject.Inject

class ChatMapper @Inject constructor() {

    fun fromTempChatModelToChatResponse(model: TempChatModel):ChatResponse{
        return ChatResponse(
            status = model.status,
            id = model.id,
            idLocal = model.idLocal,
            uniqueRoomId = model.uniqueRoomId,
            sendBy = ChatResponse.SendBy(
                id = model.sendBy?.id,
                fullName = model.sendBy?.fullName,
                photo = model.sendBy?.photo
            ),
            chat = model.chat,
            repliedTo = if (model.repliedTo != null) fromTempChatModelToChatResponse(model = (model.repliedTo!!).apply { this.repliedTo = null }) else null,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    fun fromChatResponseToTempChatModel(response: ChatResponse):TempChatModel{
        return TempChatModel(
            status = response.status,
            id = response.id,
            idLocal = response.idLocal,
            uniqueRoomId = response.uniqueRoomId,
            sendBy = TempChatModel.SendBy(
                id = response.sendBy?.id,
                fullName = response.sendBy?.fullName,
                photo = response.sendBy?.photo
            ),
            chat = response.chat,
            repliedTo = if (response.repliedTo != null)  fromChatResponseToTempChatModel(response.repliedTo!!) else null,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt
        )
    }

    fun fromListChatResponseToChatModel(list:ArrayList<ChatResponse>):ArrayList<ChatModel>{
        try {
            val map  = HashMap<String, Boolean>()
            val chats:ArrayList<ChatModel> = arrayListOf()
            list.sortedBy {
                val date = it.createdAt?.toCalendar2()?.time
                date
            }.forEach {
                val date = it.createdAt?.toCalendar2()
                if (date != null && date.time.formatDate() != null && map[date.time.formatDate()] == null){
                    map[date.time.formatDate()?:""] = true
                    chats.add(ChatModel(date = date.time))
                }
                chats.add(ChatModel(chat = it))
            }
            return chats
        }catch (e:Exception){
            return arrayListOf()
        }
    }

    fun refreshListChatModel(list: ArrayList<ChatModel>):ArrayList<ChatModel>{
        return try {
            val chats:ArrayList<ChatResponse> = arrayListOf()
            list.forEach {
                if (it.chat != null)
                    chats.add(it.chat!!)
            }
            fromListChatResponseToChatModel(chats)
        }catch (e:Exception){
            arrayListOf()
        }
    }
}