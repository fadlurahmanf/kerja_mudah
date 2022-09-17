package com.app.kerja_mudah.ui.chat.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse

data class ChatRoomState(
    var chatRoomState:BaseState = BaseState.IDLE,
    var chatRoom:ChatRoomResponse ?= null,
    var errorChatRoom:String ?= null,

    var sendChatState:BaseState = BaseState.IDLE,
    var chatError:ChatResponse ?= null,
    var errorSendChat:String ?= null,

    var onReceivedMessageState: BaseState = BaseState.IDLE,
    var chatReceived:ChatResponse ?= null
)
