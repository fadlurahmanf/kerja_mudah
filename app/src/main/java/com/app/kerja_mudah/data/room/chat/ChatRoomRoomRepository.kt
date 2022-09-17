package com.app.kerja_mudah.data.room.chat

import android.content.Context
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject

class ChatRoomRoomRepository @Inject constructor(
    var context: Context
) {
    var instance: CoreDatabase = CoreDatabase.getDataBase(context)

    fun insertAll(list:ArrayList<ChatRoomResponse>) = instance.chatRoomDao().insertAll(list)

    fun getById(id:Int) = instance.chatRoomDao().getById(id)

}