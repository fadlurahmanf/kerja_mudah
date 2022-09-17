package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.media.AlbumModel
import com.bumptech.glide.Glide

class AlbumAdapter(var list:ArrayList<AlbumModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack:CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class RecyclerViewHolder(view:View):RecyclerView.ViewHolder(view){
        var iv:ImageView = view.findViewById(R.id.iv)
        var albumName:TextView = view.findViewById(R.id.tv_album_name)
        var totalMedia:TextView = view.findViewById(R.id.tv_total_media)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = list[position]

        val mHolder = holder as RecyclerViewHolder

        Glide.with(mHolder.iv)
            .load(album.medias?.firstOrNull()?.path)
            .centerCrop()
            .into(mHolder.iv)

        mHolder.albumName.text = album.bucketName
        mHolder.totalMedia.text = album.medias?.size?.toString()

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(album)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(album: AlbumModel?)
    }
}