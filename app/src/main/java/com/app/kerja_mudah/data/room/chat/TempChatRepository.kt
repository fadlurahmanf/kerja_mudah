package com.app.kerja_mudah.data.room.chat

import android.content.Context
import com.app.kerja_mudah.data.model.chat.ChatModel
import com.app.kerja_mudah.data.model.chat.TempChatModel
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject


class TempChatRepository @Inject constructor(
    var context: Context
) {
    private var instance = CoreDatabase.getDataBase(context)

    fun insertAll(list: List<TempChatModel>) = instance.tempChatDao().insertAll(list)

    fun deleteChatByIdLocal(idLocal:Long) = instance.tempChatDao().deleteByChatId(idLocal)

    fun selectByUniqueRoomId(uniqueRoomId:String) = instance.tempChatDao().selectByUniqueRoomId(uniqueRoomId)

    fun updateStatus(status:String, idLocal: Long) = instance.tempChatDao().update(status, idLocal)

    fun deleteAllByUniqueRoomId(uniqueRoomId: String) = instance.tempChatDao().deleteAllByUniqueRoomId(uniqueRoomId)
}