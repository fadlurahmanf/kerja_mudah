package com.app.kerja_mudah.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.model.chat.TempChatModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.model.freelancer.FreelancerRemoteKeyModel
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import com.app.kerja_mudah.data.room.chat.ChatRoomDao
import com.app.kerja_mudah.data.room.chat.TempChatDao
import com.app.kerja_mudah.data.room.converter.*
import com.app.kerja_mudah.data.room.freelancer.FreelancerDao
import com.app.kerja_mudah.data.room.freelancer.FreelancerRemoteKeyDao
import com.app.kerja_mudah.data.room.job.JobDao
import com.app.kerja_mudah.data.room.service.ServiceOrderDao

@Database(
    entities = [
        JobCategoryResponse::class,
        ChatRoomResponse::class,
        FreelancerModel::class,
        FreelancerRemoteKeyModel::class,
        TempChatModel::class,
        ServiceOrderResponse::class,
    ], version = CoreDatabase.VERSION
)
@TypeConverters(value = [
    RoomConverter::class,
    AuthConverter::class,
    FreelanceConverter::class,
    ChatConverter::class,
    TempChatModelConverter::class,
    ServiceConverter::class
])
abstract class CoreDatabase : RoomDatabase() {

    abstract fun dao(): JobDao
    abstract fun freelancerDao(): FreelancerDao
    abstract fun freelancerRemoteKeyDao(): FreelancerRemoteKeyDao
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun tempChatDao(): TempChatDao
    abstract fun serviceOrderDao(): ServiceOrderDao

    companion object{
        const val VERSION = 14
        @Volatile
        private var INSTANCE: CoreDatabase?= null
        fun getDataBase(context: Context): CoreDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoreDatabase::class.java,
                    ParamsRoom.CORE_DATABASE
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}