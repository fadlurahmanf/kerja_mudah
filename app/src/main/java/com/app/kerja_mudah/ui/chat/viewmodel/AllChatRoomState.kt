package com.app.kerja_mudah.ui.chat.viewmodel

import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse

data class AllChatRoomState(
    var chatRoomState:BaseState = BaseState.IDLE,
    var roomChatList:ArrayList<ChatRoomResponse> ?= null,
    var errorChatRoomState:String ?= null
)
