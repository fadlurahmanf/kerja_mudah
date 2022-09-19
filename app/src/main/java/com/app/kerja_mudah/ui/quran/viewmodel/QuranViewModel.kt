package com.app.kerja_mudah.ui.quran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.quran.QuranEntity
import com.app.kerja_mudah.data.response.quran.SurahResponse
import com.app.kerja_mudah.data.response.quran.TafsirResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class QuranViewModel @Inject constructor(
    var quranEntity: QuranEntity
) : BaseViewModel() {

    private var stateData = QuranState()

    private var _state:MutableLiveData<QuranState> = MutableLiveData<QuranState>()
    val state:LiveData<QuranState> = _state

    fun getListSurah(){
        stateData.getListSurahState = BaseState.LOADING
        _state.value = stateData
        disposable().add(quranEntity.getListSurah()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stateData.getListSurahState = BaseState.SUCCESS
                    stateData.listSurah = it
                    _state.value = stateData
                },
                {
                    stateData.getListSurahState = BaseState.FAILED
                    stateData.errorListSurah = it.message
                    _state.value = stateData
                    stateData.getListSurahState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getListSurahState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun getDetailSurah(nomor:Int, playAudio:Boolean = false){
        stateData.getDetailSurah = BaseState.LOADING
        _state.value = stateData
        disposable().add(quranEntity.getDetailSurah(nomor)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stateData.getDetailSurah = BaseState.SUCCESS
                    stateData.detailSurah = it
                    stateData.playAudio = playAudio
                    _state.value = stateData
                },
                {
                    stateData.errorDetailSurah = it.message
                    stateData.getDetailSurah = BaseState.FAILED
                    _state.value = stateData
                    stateData.getDetailSurah = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getDetailSurah = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun getTafsir(nomor:Int){
        stateData.getTafsirSurah = BaseState.LOADING
        _state.value = stateData
        Observable.zip(
            quranEntity.getDetailSurah(nomor),
            quranEntity.getTafsir(nomor),
            BiFunction { t1, t2 ->
                SurahResponse(
                    nomor = t1.nomor,
                    nama = t1.nama,
                    namaLatin = t1.namaLatin,
                    jumlahAyat = t1.jumlahAyat,
                    tempatTurun = t1.tempatTurun,
                    arti = t1.arti,
                    deskripsi = t1.deskripsi,
                    audio = t1.audio,
                    ayat = t1.ayat,
                    tafsir = t2.tafsir
                )
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stateData.getTafsirSurah = BaseState.SUCCESS
                    stateData.tafsirSurah = it
                    _state.value = stateData
                },
                {
                    stateData.getTafsirSurah = BaseState.FAILED
                    stateData.errorTafsirSurah = it.message
                    _state.value = stateData
                    stateData.getTafsirSurah = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.getTafsirSurah = BaseState.IDLE
                    _state.value = stateData
                }
            )
    }

}