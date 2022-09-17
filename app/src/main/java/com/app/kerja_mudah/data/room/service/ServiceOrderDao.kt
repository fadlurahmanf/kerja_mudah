package com.app.kerja_mudah.data.room.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kerja_mudah.core.constant.ParamsRoom
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse

@Dao
interface ServiceOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list:ArrayList<ServiceOrderResponse>)

    @Query(value = "SELECT * FROM ${ParamsRoom.SERVICE_ORDER_TABLE}")
    fun getAllServiceOrder():List<ServiceOrderResponse>

    @Query("DELETE FROM ${ParamsRoom.SERVICE_ORDER_TABLE}")
    fun deleteAll()
}