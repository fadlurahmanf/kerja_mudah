package com.app.kerja_mudah.data.room.freelancer

import android.content.Context
import com.app.kerja_mudah.data.model.freelancer.FreelancerRemoteKeyModel
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject

class FreelancerRemoteKeyRepository @Inject constructor(
    var context: Context
) {
    companion object{
        var instance: CoreDatabase ?= null
    }
    init {
        instance = CoreDatabase.getDataBase(context)
    }

    fun insertAll(list:ArrayList<FreelancerRemoteKeyModel>) = instance?.freelancerRemoteKeyDao()?.insertAll(list)

    fun getRemoteKeyByFreelancer(freelancerId:Int) = instance?.freelancerRemoteKeyDao()?.getRemoteKeyByFreelancer(freelancerId)

    fun clear() = instance?.freelancerRemoteKeyDao()?.clear()
}