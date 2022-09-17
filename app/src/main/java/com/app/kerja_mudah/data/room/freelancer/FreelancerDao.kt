package com.app.kerja_mudah.data.room.freelancer

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse

@Dao
interface FreelancerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list:ArrayList<FreelancerModel>)

    @Query("SELECT * FROM ${ParamsRoom.FREELANCER_TABLE} ORDER BY id ASC")
    fun selectAll():PagingSource<Int, FreelancerModel>

    @Query("DELETE FROM ${ParamsRoom.FREELANCER_TABLE}")
    fun remove()

}