package com.app.kerja_mudah.data.entity.chat

import com.app.kerja_mudah.base.network.AbstractNetwork
import com.app.kerja_mudah.base.network.AuthAbstractNetwork
import com.app.kerja_mudah.data.api.chat.ChatApi
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import javax.inject.Inject

class ChatEntity @Inject constructor(
    authRepository: AuthRepository
):AuthAbstractNetwork<ChatApi>(authRepository) {
    override fun getApi(): Class<ChatApi> {
        return ChatApi::class.java
    }

    fun getDetailChat(opponentId:Int) = networkService(15).getDetailRoomChat(opponentId)

    fun sendChat(opponentId:Int, body: List<MultipartBody.Part>) = networkService(60).sendChat(opponentId, body)

    fun getAllRoomChat() = networkService(15).getAllRoomChat(token = authRepository.accessToken?:"")
}