package com.app.kerja_mudah.data.room.service

import android.content.Context
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse
import com.app.kerja_mudah.data.room.CoreDatabase
import javax.inject.Inject

class ServiceOrderRoomRepository @Inject constructor(
    var context: Context
) {
    companion object{
        var instance: CoreDatabase?= null
    }
    init {
        instance = CoreDatabase.getDataBase(context)
    }

    fun insertAll(list: ArrayList<ServiceOrderResponse>) = instance?.serviceOrderDao()?.insertAll(list)

    fun getAll() = instance?.serviceOrderDao()?.getAllServiceOrder()

    fun deleteAll() = instance?.serviceOrderDao()?.deleteAll()
}