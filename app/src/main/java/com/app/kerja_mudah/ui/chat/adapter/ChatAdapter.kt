package com.app.kerja_mudah.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.*
import com.app.kerja_mudah.custom_lib.CustomGlideCacheKey
import com.app.kerja_mudah.data.model.chat.ChatModel
import com.app.kerja_mudah.data.response.chat.ChatResponse
import com.bumptech.glide.Glide


class ChatAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listChat:ArrayList<ChatModel> = arrayListOf()
    private var myUserId:Int = -1

    fun setMyUserId(id:Int){
        myUserId = id
    }

    fun setListChat(list:ArrayList<ChatModel>){
        listChat.clear()
        listChat.addAll(list)
        notifyDataSetChanged()
    }

    fun refreshDataByIdLocal(chatModel:ChatModel){
        for (element in 0..listChat.size){
            if (listChat[element].chat?.idLocal == chatModel.chat?.idLocal){
                listChat[element] = chatModel
                notifyItemChanged(element)
                break
            }
        }
    }

    companion object{
        const val IDLE = "IDLE"
        const val SELECTING = "SELECTING"
    }

    private lateinit var context:Context
    private var callBack: CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class DateViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvChatDate:TextView = view.findViewById(R.id.tv_date)
        val rvChat:RecyclerView = view.findViewById(R.id.rv_chat)
    }

    inner class LeftViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvChatText:TextView = view.findViewById(R.id.tv_text_chat)
        val tvCreatedAt:TextView = view.findViewById(R.id.tv_chat_created_at)
        val ll1:LinearLayout = view.findViewById(R.id.ll1)
        val iv1:ImageView = view.findViewById(R.id.iv_1)
        val iv2:ImageView = view.findViewById(R.id.iv_2)
        val iv3:ImageView = view.findViewById(R.id.iv_3)
        val iv4:ImageView = view.findViewById(R.id.iv_4)
        val chatSelected:ImageView = view.findViewById(R.id.iv_chat_selected)
        val chatNotSelected:ImageView = view.findViewById(R.id.iv_chat_not_selected)
    }

    inner class RightViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvChatText:TextView = view.findViewById(R.id.tv_text_chat)
        val tvCreatedAt:TextView = view.findViewById(R.id.tv_chat_created_at)
        val ivStatus:ImageView = view.findViewById(R.id.iv_status)
        val llSecond:LinearLayout = view.findViewById(R.id.ll_second)
        val ll1:LinearLayout = view.findViewById(R.id.ll1)
        val ll2:LinearLayout = view.findViewById(R.id.ll2)
        val iv1:ImageView = view.findViewById(R.id.iv_1)
        val iv2:ImageView = view.findViewById(R.id.iv_2)
        val iv3:ImageView = view.findViewById(R.id.iv_3)
        val iv4:ImageView = view.findViewById(R.id.iv_4)
        val chatSelected:ImageView = view.findViewById(R.id.iv_chat_selected)
        val chatNotSelected:ImageView = view.findViewById(R.id.iv_chat_not_selected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val leftView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_left, parent, false)
        val rightView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
        val dateView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_date, parent, false)
        return when(viewType){
            0 -> DateViewHolder(dateView)
            1 -> RightViewHolder(rightView)
            else -> LeftViewHolder(leftView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = listChat[position]
        val createdAt = chat.chat?.createdAt?.toCalendar2()?.time?.formatDate4()?:""
        if (holder.itemViewType == 0){
            val mHolder = holder as DateViewHolder
            mHolder.tvChatDate.text = chat.date?.formatDate() ?: ""
        }else if (holder.itemViewType == 1){
            val mHolder = holder as RightViewHolder
            mHolder.tvChatText.text = chat.chat?.chat
            mHolder.tvCreatedAt.text = createdAt
            mHolder.llSecond.background = ContextCompat.getDrawable(context, R.drawable.solid_green_corner_5)
            if (chat.chat?.status == "loading"){
                mHolder.ivStatus.setImageResource(R.drawable.ic_waiting)
                mHolder.ivStatus.visibility = View.VISIBLE
            } else if (chat.chat?.status == "failed"){
                mHolder.ivStatus.visibility = View.INVISIBLE
                mHolder.llSecond.background = ContextCompat.getDrawable(context, R.drawable.solid_red_corner_5)
            }else{
                mHolder.ivStatus.visibility = View.VISIBLE
                mHolder.ivStatus.setImageResource(R.drawable.ic_double_check)
            }

            mHolder.itemView.setOnClickListener {
                if (chat.chat != null){
                    callBack?.onClicked(chat)
                }
            }

            if (chat.chat?.chat?.isNullOrEmpty() == true){
                mHolder.tvChatText.visibility = View.GONE
            }else{
                mHolder.tvChatText.visibility = View.VISIBLE
            }

//            if(state.isEmpty() || state.first() == IDLE){
//                mHolder.chatSelected.visibility = View.GONE
//                mHolder.chatNotSelected.visibility = View.GONE
//            }else if (chatSelected.firstOrNull { it == chat.id } != null){
//                mHolder.chatSelected.visibility = View.VISIBLE
//                mHolder.chatNotSelected.visibility = View.GONE
//            }else{
//                mHolder.chatSelected.visibility = View.GONE
//                mHolder.chatNotSelected.visibility = View.VISIBLE
//            }

//            if ((chat.chat?.media?.size?:0) > 2){
//                mHo
//            }

            val media:ArrayList<ChatResponse.Media>? = chat.chat?.media

            if ((media?.size?:0) > 2){
                mHolder.ll2.visibility = View.VISIBLE
                if ((media?.size?:0) >= 3){
                    Glide.with(context).load(customGlideUrl(media?.get(2)?.url?:"")).centerCrop().into(mHolder.iv3)
                    mHolder.iv3.visibility = View.VISIBLE
                    mHolder.iv3.setOnClickListener {
                        callBack?.onMediaClicked(media = media, position = 2)
                    }
                }
                if ((media?.size?:0) >= 4){
                    Glide.with(context).load(customGlideUrl(media?.get(3)?.url?:"")).centerCrop().into(mHolder.iv4)
                    mHolder.iv4.visibility = View.VISIBLE
                    mHolder.iv4.setOnClickListener {
                        callBack?.onMediaClicked(media = media, position = 3)
                    }
                }
            }

            if ((media?.size?:0) > 0){
                mHolder.ll1.visibility = View.VISIBLE
                if ((media?.size?:0) >= 1){
                    Glide.with(context).load(customGlideUrl(media?.get(0)?.url?:"")).centerCrop().into(mHolder.iv1)
                    mHolder.iv1.visibility = View.VISIBLE
                    mHolder.iv1.setOnClickListener {
                        callBack?.onMediaClicked(media = media, position = 0)
                    }
                }
                if ((chat.chat?.media?.size?:0) >= 2){
                    Glide.with(context).load(customGlideUrl(media?.get(1)?.url?:"")).centerCrop().into(mHolder.iv2)
                    mHolder.iv2.visibility = View.VISIBLE
                    mHolder.iv2.setOnClickListener {
                        callBack?.onMediaClicked(media = media, position = 1)
                    }
                }
            }
//            if ((chat.images?.size?:0) > 0){
//                mHolder.ll1.visibility = View.VISIBLE
//                if ((chat.images?.size?:0) == 1){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    mHolder.iv1.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 2){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 3){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(2)?:"")).centerCrop().into(mHolder.iv3)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                    mHolder.iv3.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 4){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(2)?:"")).centerCrop().into(mHolder.iv3)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(3)?:"")).centerCrop().into(mHolder.iv4)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                    mHolder.iv3.visibility = View.VISIBLE
//                    mHolder.iv4.visibility = View.VISIBLE
//                }
//            }else{
//                mHolder.ll1.visibility = View.GONE
//            }
        }else if (holder.itemViewType == 2){
            val mHolder = holder as LeftViewHolder
            mHolder.tvChatText.text = chat.chat?.chat ?: ""
            mHolder.tvCreatedAt.text = createdAt

//            if(chat.text.isNullOrEmpty()) mHolder.tvChatText.visibility = View.GONE
//            else mHolder.tvChatText.visibility = View.VISIBLE
//
//            if(state.isEmpty() || state.first() == IDLE){
//                mHolder.chatSelected.visibility = View.GONE
//                mHolder.chatNotSelected.visibility = View.GONE
//            }else if (chatSelected.firstOrNull { it == chat.id } != null){
//                mHolder.chatSelected.visibility = View.VISIBLE
//                mHolder.chatNotSelected.visibility = View.GONE
//            }else{
//                mHolder.chatSelected.visibility = View.GONE
//                mHolder.chatNotSelected.visibility = View.VISIBLE
//            }
//
//            if ((chat.images?.size?:0) > 0){
//                mHolder.ll1.visibility = View.VISIBLE
//                if ((chat.images?.size?:0) == 1){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    mHolder.iv1.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 2){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 3){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(2)?:"")).centerCrop().into(mHolder.iv3)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                    mHolder.iv3.visibility = View.VISIBLE
//                }else if ((chat.images?.size?:0) == 4){
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(0)?:"")).centerCrop().into(mHolder.iv1)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(1)?:"")).centerCrop().into(mHolder.iv2)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(2)?:"")).centerCrop().into(mHolder.iv3)
//                    Glide.with(context).load(handleImageVideoUrl(chat.images?.get(3)?:"")).centerCrop().into(mHolder.iv4)
//                    mHolder.iv1.visibility = View.VISIBLE
//                    mHolder.iv2.visibility = View.VISIBLE
//                    mHolder.iv3.visibility = View.VISIBLE
//                    mHolder.iv4.visibility = View.VISIBLE
//                }
//            }else{
//                mHolder.ll1.visibility = View.GONE
//            }
        }
//
//        holder.itemView.setOnClickListener {
//            callBack.onClicked(chat)
//        }
//
//        holder.itemView.setOnLongClickListener {
//            callBack.onLongClicked(chat)
//            true
//        }
    }

    private fun customGlideUrl(url:String):Any{
        return if (url.contains("http")){
            CustomGlideCacheKey(url)
        }else{
            url
        }
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun getItemViewType(position: Int): Int {
        val chat = listChat[position]
        return when {
            chat.date != null -> {
                0
            }
            chat.chat?.sendBy?.id == myUserId -> {
                1
            }
            else -> {
                2
            }
        }
    }

    interface CallBack{
        fun onLongClicked(chat:ChatResponse){}
        fun onClicked(chat: ChatModel){}
        fun onMediaClicked(media: ArrayList<ChatResponse.Media>?, position:Int?){}
    }
}