package com.app.kerja_mudah.data.model.chat

import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.google.gson.annotations.SerializedName
import java.util.*

data class ChatModel(
    var date:Date ?= null,
    var chat:ChatResponse ?= null,
)