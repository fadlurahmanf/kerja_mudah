package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide
import java.io.File

class PreviewSelectedMediaAdapter(var list:ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var iv:ImageView = view.findViewById(R.id.iv)
        var video:ImageView = view.findViewById(R.id.iv_video)
        var delete:ImageView = view.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preview_media_selected, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val path = list[position]

        val mHolder = holder as ViewHolder

        Glide.with(mHolder.iv)
            .load(File(path))
            .centerCrop()
            .into(mHolder.iv)

        if (path.contains(".mp4", true)){
            mHolder.video.visibility = View.VISIBLE
        }else{
            mHolder.video.visibility = View.GONE
        }

        mHolder.delete.setOnClickListener {
            callBack.onDeleteClicked(path)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onDeleteClicked(path:String)
    }
}