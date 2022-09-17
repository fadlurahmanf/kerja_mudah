package com.app.kerja_mudah.data.entity.job

import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.job.JobApi
import com.google.gson.JsonObject
import javax.inject.Inject

class JobEntity @Inject constructor():AbstractNetwork<JobApi>() {
    override fun getApi(): Class<JobApi> {
        return JobApi::class.java
    }

    fun getAllJobCategory(body: JsonObject) = networkService(30).getAllJobCategory(body)

    fun getAllJob() = networkService(30).getAllJob()
}