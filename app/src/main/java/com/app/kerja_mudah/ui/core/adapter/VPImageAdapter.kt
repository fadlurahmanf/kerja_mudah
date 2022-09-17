package com.app.kerja_mudah.ui.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide


class VPImageAdapter():RecyclerView.Adapter<VPImageAdapter.ViewHolder>() {
    private lateinit var context:Context

    private var list:ArrayList<String> = arrayListOf()

    private var callBack: CallBack ?= null

    fun setList(list: ArrayList<String>){
        this.list.clear();
        this.list.addAll(list)
        notifyItemInserted(list.size)
    }

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var ivImage:ImageView = view.findViewById(R.id.iv_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_full_width, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoUrl:String = list[position]

        Glide.with(holder.ivImage)
            .load(photoUrl)
            .placeholder(ContextCompat.getDrawable(context, R.drawable.light_grey_solid))
            .into(holder.ivImage)

        if (callBack != null){
            holder.itemView.setOnClickListener {
                callBack?.onClicked(photoUrl)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(image:String)
    }
}