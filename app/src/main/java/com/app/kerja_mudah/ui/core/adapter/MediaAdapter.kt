package com.app.kerja_mudah.ui.core.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.MediaModel
import com.bumptech.glide.Glide

class MediaAdapter(var list:ArrayList<MediaModel>, var multipleSelect:Boolean = false, var selected:ArrayList<MediaModel> = arrayListOf()):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack: CallBack
    private lateinit var context:Context

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class RecyclerViewHolder(view:View):RecyclerView.ViewHolder(view){
        var image :ImageView = view.findViewById(R.id.iv)
        var signSelected:ImageView = view.findViewById(R.id.iv_sign_selected)
        var video:ImageView = view.findViewById(R.id.iv_video)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media_for_select, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media:MediaModel = list[position]

        val mHolder = holder as RecyclerViewHolder

        Glide.with(mHolder.image)
            .load(media.path)
            .centerCrop()
            .into(mHolder.image)

        if (multipleSelect){
            mHolder.signSelected.visibility = View.VISIBLE
        }else{
            mHolder.signSelected.visibility = View.GONE
        }

        if (media.path?.contains(".mp4", ignoreCase = true) == true){
            mHolder.video.visibility = View.VISIBLE
        }else{
            mHolder.video.visibility = View.GONE
        }

        if (selected.firstOrNull { it.id == media.id } != null){
            mHolder.signSelected.setImageResource(R.drawable.circle_selected)
        }else{
            mHolder.signSelected.setImageResource(R.drawable.circle_unselected)
        }

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(media)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(media:MediaModel?)
    }
}