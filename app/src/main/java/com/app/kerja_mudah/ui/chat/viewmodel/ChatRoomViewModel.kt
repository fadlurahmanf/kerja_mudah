package com.app.kerja_mudah.ui.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.base.BaseViewModel
import com.app.kerja_mudah.data.entity.chat.ChatEntity
import com.app.kerja_mudah.data.mapper.ChatMapper
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.repository.chat.ChatDataSource
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.room.chat.TempChatRepository
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class ChatRoomViewModel @Inject constructor(
    var chatDataSource: ChatDataSource,
    var chatEntity: ChatEntity,
    var tempChatRepository: TempChatRepository,
    var chatMapper: ChatMapper,
    var authRepository: AuthRepository
):BaseViewModel() {
    private var stateData = ChatRoomState()

    private var _state = MutableLiveData<ChatRoomState>(stateData)
    val state get() : LiveData<ChatRoomState> = _state

    fun initWebSocket(channelName:String, channelEvent:String){
        val pusher = Pusher("5a83c2c40bcef92021ee", pusherOptions())

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
            }
            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.i("Pusher", "There was a problem connecting! code ($code), message ($message), exception($e)")
            }
        }, ConnectionState.ALL)

        val channel = pusher.subscribe(channelName)
        Log.i("Pusher","Channel Subscribe to $channelName and event $channelEvent")
        channel.bind(channelEvent) { event ->
            Log.i("Pusher","Received event with data: $event")
            val json = JSONObject("""${event.data}""")
            GlobalScope.launch {
                withContext(Dispatchers.Main){
                    val newChat : ChatResponse? = Gson().fromJson<ChatResponse>(json.getString("chat"), ChatResponse::class.java)
                    stateData.onReceivedMessageState = BaseState.SUCCESS
                    stateData.chatReceived = newChat
                    _state.value = stateData

                    stateData.onReceivedMessageState = BaseState.IDLE
                    _state.value = stateData
                }
            }
        }
    }

    fun getDetailChatRoom(opponentId:Int){
        var uniqueRoomId = "";
        if (authRepository.myProfile?.id == null){
            stateData.chatRoomState = BaseState.FAILED
            stateData.errorChatRoom = "Missing variable"
            _state.value = stateData
            return
        }
        if ((authRepository.myProfile?.id?:-1) < opponentId ){
            uniqueRoomId = "${authRepository.myProfile?.id}_${opponentId}"
        }else{
            uniqueRoomId = "${opponentId}_${authRepository.myProfile?.id}"
        }

        stateData.chatRoomState = BaseState.LOADING
        _state.postValue(stateData)
        disposable().add(chatDataSource.getDetailChat(opponentId, uniqueRoomId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.chatRoomState = BaseState.SUCCESS
                        stateData.chatRoom = it.data
                        _state.value = stateData
                    }else{
                        stateData.chatRoomState = BaseState.FAILED
                        stateData.errorChatRoom = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.chatRoomState = BaseState.FAILED
                    stateData.errorChatRoom = it.message
                    _state.value = stateData
                },
                {
                    stateData.chatRoomState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun sendChat(to:Int, chatResponse: ChatResponse){
        stateData.sendChatState = BaseState.LOADING
        _state.value = stateData

        val builder = MultipartBody.Builder()
        if ((chatResponse.chat?:"").isNotEmpty()){
            builder.addFormDataPart("content", chatResponse.chat?:"")
        }

        disposable().add(chatDataSource.sendChat(opponentId = to, chatResponse = chatResponse, body = builder.build().parts)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.code == 200 && it.message == "success"){
                        stateData.sendChatState = BaseState.SUCCESS
                        _state.value = stateData
                    }else{
                        stateData.sendChatState = BaseState.FAILED
                        stateData.errorSendChat = it.message
                        _state.value = stateData
                    }
                },
                {
                    stateData.sendChatState = BaseState.FAILED
                    stateData.chatError = chatResponse
                    stateData.errorSendChat = it.message
                    _state.value = stateData
                    stateData.sendChatState = BaseState.IDLE
                    _state.value = stateData
                },
                {
                    stateData.sendChatState = BaseState.IDLE
                    _state.value = stateData
                }
            ))
    }

    fun disconnectWebSocket(){
        pusher().disconnect()
    }
}