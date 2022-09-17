package com.app.kerja_mudah.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.formatDate2
import com.app.kerja_mudah.core.extension.isoDateTimeToDate
import com.app.kerja_mudah.data.response.chat.ChatRoomResponse
import com.bumptech.glide.Glide

class ChatRoomAdapter(var list:ArrayList<ChatRoomResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.iv_profile_picture)
        val name:TextView = view.findViewById(R.id.tv_name)
        val lastChat:TextView = view.findViewById(R.id.tv_last_chat)
        val updatedAt:TextView = view.findViewById(R.id.tv_last_updated_at)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatRoom = list[position]

        val mHolder = holder as ViewHolder

//        Glide.with(mHolder.image)
//            .load(chatRoom.otherUser?.photo)
//            .into(mHolder.image)
//
//        mHolder.name.text = chatRoom.otherUser?.email?:""
//        mHolder.lastChat.text = chatRoom.lastChat
//        mHolder.updatedAt.text = chatRoom.updatedAt?.isoDateTimeToDate()?.formatDate2()?:""

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(chatRoom)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(chatRoom:ChatRoomResponse?)
    }
}