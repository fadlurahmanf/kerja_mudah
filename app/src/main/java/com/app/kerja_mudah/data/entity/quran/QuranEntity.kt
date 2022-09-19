package com.app.kerja_mudah.data.entity.quran

import android.content.Context
import com.app.kerja_mudah.base.network.AbstractQuranNetwork
import com.app.kerja_mudah.data.api.quran.QuranAPI
import javax.inject.Inject

class QuranEntity @Inject constructor(
    var context: Context
) : AbstractQuranNetwork<QuranAPI>() {
    override fun getApi(): Class<QuranAPI> {
        return QuranAPI::class.java
    }

    fun getListSurah() = networkService(30).getListSurah()

    fun getDetailSurah(nomor:Int) = networkService(30).getSurah(nomor)
}