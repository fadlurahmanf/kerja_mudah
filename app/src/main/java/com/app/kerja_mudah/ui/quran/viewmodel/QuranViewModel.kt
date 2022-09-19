package com.app.kerja_mudah.ui.quran.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.quran.QuranEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

}