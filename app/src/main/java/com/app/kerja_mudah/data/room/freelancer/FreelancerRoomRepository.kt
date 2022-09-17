package com.app.kerja_mudah.data.room.freelancer

import android.content.Context
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject


class FreelancerRoomRepository @Inject constructor(
    var context: Context
) {
    var instance:CoreDatabase = CoreDatabase.getDataBase(context)

    fun insertAll(list:ArrayList<FreelancerModel>) = instance.freelancerDao().insertAll(list)

    fun selectAll() = instance.freelancerDao().selectAll()

    fun clear() = instance.freelancerDao().remove()
}