package com.app.kerja_mudah.data.repository.chat

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import com.app.kerja_mudah.data.entity.chat.ChatEntity
import com.app.kerja_mudah.data.mapper.ChatMapper
import com.app.kerja_mudah.data.model.chat.TempChatModel
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.room.CoreDatabase
import com.app.kerja_mudah.data.room.chat.TempChatRepository
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatDataSource @Inject constructor(
    private val context:Context,
    private val chatEntity: ChatEntity,
    private val tempChatRepository: TempChatRepository,
    private val chatMapper: ChatMapper
) {

    companion object{
        val TAG = ChatDataSource::class.java.simpleName
    }

    fun getDetailChat(opponentId:Int, uniqueRoomId: String):Observable<BaseResponse<ChatRoomResponse>>{
        val apiResult = getDetailChatApi(opponentId)
        return Observable.zip(
            apiResult,
            getTempChatByUniqueRoomId(uniqueRoomId),
            BiFunction { t1, t2 ->
                val list: List<ChatResponse> = t2.map {
                    println("masuk bifunction ${Gson().toJson(it)}")
                    chatMapper.fromTempChatModelToChatResponse(it)
                }
                t1.apply {
                    this.data?.chats?.addAll(list)
                }
            }
        ).subscribeOn(Schedulers.io())
            .doOnNext {
//                tempChatRepository.deleteAllByUniqueRoomId(uniqueRoomId)
            }

    }

    fun getTempChatByUniqueRoomId(uniqueRoomId:String): Observable<List<TempChatModel>> {
        return Observable.just(tempChatRepository)
            .subscribeOn(Schedulers.io())
            .map {
                it.selectByUniqueRoomId(uniqueRoomId)
            }
    }

    fun getDetailChatApi(opponentId:Int): Observable<BaseResponse<ChatRoomResponse>> {
        return chatEntity.getDetailChat(opponentId)
            .subscribeOn(Schedulers.io())
            .doOnNext {

            }
    }

    fun sendChat(opponentId:Int, chatResponse: ChatResponse, body:List<MultipartBody.Part>): Observable<BaseResponse<Nullable>> {
        val list:List<TempChatModel> = listOf(chatMapper.fromChatResponseToTempChatModel(response = chatResponse))
        insertTempChat(list)
        return chatEntity.sendChat(opponentId, body)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                if (it.code == 200 && it.message == "success"){
                    if (chatResponse.idLocal != null){
                        tempChatRepository.deleteChatByIdLocal(chatResponse.idLocal?:-1)
                    }
                }else{
                    if (chatResponse.idLocal != null){
                        tempChatRepository.updateStatus("failed", chatResponse.idLocal?:-1)
                    }
                }
            }.doOnError {
                Log.d(TAG, "doOnError SendChat ${chatResponse.idLocal}")
                if (chatResponse.idLocal != null){
                    tempChatRepository.updateStatus("failed", chatResponse.idLocal?:-1)
                }
            }
    }

    private fun insertTempChat(list:List<TempChatModel>){
        CoreDatabase.getDataBase(context).tempChatDao()
            .insertAll(list)
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .doOnComplete {
                Log.d(TAG, "insertTempChat")
            }.doOnError {
                Log.e(TAG, "insertTempChat: ${it.message}")
            }.subscribe().let {
                CompositeDisposable().add(it)
            }

    }

}