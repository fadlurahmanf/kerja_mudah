package com.app.kerja_mudah.data.room.job

import android.content.Context
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject


class JobRoomRepository @Inject constructor(
    var context: Context
) {
    companion object{
        var instance: CoreDatabase?= null
    }
    init {
        instance = CoreDatabase.getDataBase(context)
    }

    suspend fun insertAll(allJobCategories:List<JobCategoryResponse>) = instance?.dao()?.insertAll(allJobCategories)

    suspend fun deleteAll() = instance?.dao()?.deleteAll()

    suspend fun getAll() = instance?.dao()?.getAllJobCategories()

    suspend fun getAllServicesJob() = instance?.dao()?.getAllServicesJob()
}