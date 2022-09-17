package com.app.kerja_mudah.data.entity.core

import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.data.api.core.CoreApi
import com.google.gson.JsonObject
import javax.inject.Inject

class CoreEntity @Inject constructor():AbstractNetwork<CoreApi>() {
    override fun getApi(): Class<CoreApi> {
        return CoreApi::class.java
    }

    fun getAds(flag:String) = networkService(15).getAds(flag)

    fun getLegal(id:Int) = networkService(15).getLegal(id)
}