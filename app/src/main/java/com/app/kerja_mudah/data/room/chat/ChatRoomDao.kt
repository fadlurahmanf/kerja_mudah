package com.app.kerja_mudah.data.room.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse

@Dao
interface ChatRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list:ArrayList<ChatRoomResponse>)

    @Query("SELECT * FROM ${ParamsRoom.CHAT_ROOM_TABLE} WHERE id = :id LIMIT 1")
    fun getById(id:Int) : ChatRoomResponse
}