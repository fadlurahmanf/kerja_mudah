package com.app.kerja_mudah.data.room.job

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.response.job.JobCategoryResponse

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(allJobCategories:List<JobCategoryResponse>)

    @Query("SELECT * FROM ${ParamsRoom.JOB_CATEGORY_TABLE}")
    suspend fun getAllJobCategories():List<JobCategoryResponse>

    @Query("DELETE FROM ${ParamsRoom.JOB_CATEGORY_TABLE}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${ParamsRoom.JOB_CATEGORY_TABLE} WHERE flag LIKE 'individu'")
    suspend fun getAllServicesJob():List<JobCategoryResponse>
}