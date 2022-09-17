package com.app.kerja_mudah.ui.chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.chat.ChatEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AllChatRoomViewModel @Inject constructor(
    var chatEntity: ChatEntity
) : BaseViewModel() {
    private var stateData = AllChatRoomState()

    private var _state : MutableLiveData<AllChatRoomState> = MutableLiveData<AllChatRoomState>(stateData)
    val state get() : LiveData<AllChatRoomState> = _state

    fun getAllRoomChat(){
       stateData.chatRoomState = BaseState.LOADING
        _state.value = stateData
        disposable().add(chatEntity.getAllRoomChat()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.chatRoomState = BaseState.SUCCESS
                        stateData.roomChatList = it.data
                        _state.value = stateData
                    }else{
                        stateData.chatRoomState = BaseState.FAILED
                        stateData.errorChatRoomState = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.chatRoomState = BaseState.FAILED
                    stateData.errorChatRoomState = it.message
                    _state.value = stateData
                },
                {
                    stateData.chatRoomState = BaseState.IDLE
                    stateData.errorChatRoomState = null
                    _state.value = stateData
                }
            ))
    }
}