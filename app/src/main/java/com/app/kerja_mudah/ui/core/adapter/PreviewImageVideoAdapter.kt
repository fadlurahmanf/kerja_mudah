package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide

class PreviewImageVideoAdapter(var list:ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var image:ImageView = view.findViewById(R.id.iv_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_full_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val url = list[position]
        val mHolder = holder as ViewHolder
        Glide.with(mHolder.image)
            .load(url)
            .into(mHolder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}