package com.app.kerja_mudah.data.room.chat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.model.chat.ChatModel
import com.app.kerja_mudah.data.model.chat.TempChatModel
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.DELETE


@Dao
interface TempChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list:List<TempChatModel>):Completable

    @Insert
    fun insert(chat:TempChatModel)

    @Query(value = "DELETE FROM ${ParamsRoom.TEMP_CHAT_TABLE} WHERE idLocal = :idLocal")
    fun deleteByChatId(idLocal:Long)

    @Query(value = "SELECT * FROM ${ParamsRoom.TEMP_CHAT_TABLE} WHERE uniqueRoomId = :id")
    fun selectByUniqueRoomId(id:String):List<TempChatModel>


    @Query(value = "UPDATE ${ParamsRoom.TEMP_CHAT_TABLE} SET status =:status WHERE idLocal = :idLocal")
    fun update(status:String, idLocal: Long)

    @Query(value = "DELETE FROM ${ParamsRoom.TEMP_CHAT_TABLE} WHERE uniqueRoomId = :uniqueRoomId")
    fun deleteAllByUniqueRoomId(uniqueRoomId:String)
}