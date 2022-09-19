package com.app.kerja_mudah.data.api.quran

import com.app.kerja_mudah.data.response.quran.SurahResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranAPI {
    @GET("surat")
    fun getListSurah():Observable<ArrayList<SurahResponse>>

    @GET("surat/{nomor}")
    fun getSurah(
        @Path("nomor") nomor:Int
    ):Observable<SurahResponse>

    @GET("tafsir/{nomor}")
    fun getTafsir(
        @Path("nomor") nomor: Int
    ) : Observable<SurahResponse>
}