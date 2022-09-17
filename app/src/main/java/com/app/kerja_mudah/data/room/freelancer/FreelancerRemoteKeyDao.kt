package com.app.kerja_mudah.data.room.freelancer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.model.freelancer.FreelancerRemoteKeyModel

@Dao
interface FreelancerRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list:ArrayList<FreelancerRemoteKeyModel>)

    @Query("SELECT * FROM ${ParamsRoom.FREELANCER_REMOTE_KEY_TABLE} WHERE freelancerId = :freelancerId", )
    fun getRemoteKeyByFreelancer(freelancerId:Int):FreelancerRemoteKeyModel?

    @Query("DELETE FROM ${ParamsRoom.FREELANCER_REMOTE_KEY_TABLE}")
    fun clear()
}