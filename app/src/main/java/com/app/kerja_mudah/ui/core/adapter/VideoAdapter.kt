package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide

class VideoAdapter(var list:ArrayList<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val thumbnail:ImageView = view.findViewById(R.id.iv_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = list[position]

        val mHolder = holder as ViewHolder

        Glide.with(mHolder.thumbnail)
            .load(video)
            .placeholder(R.drawable.light_green_corner_3)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(mHolder.thumbnail)

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(video)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(video:String)
    }
}