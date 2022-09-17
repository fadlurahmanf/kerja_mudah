package com.app.kerja_mudah.ui.freelancer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.custom_lib.CustomGlidePublicUrl
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.bumptech.glide.Glide

class HorizontalFreelancerReelAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list:ArrayList<FreelancerReelResponse> = arrayListOf()

    private var callback:CallBack ?= null

    private lateinit var context:Context

    fun setList(list:ArrayList<FreelancerReelResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setCallBack(callBack: CallBack){
        this.callback = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val thumbnail:ImageView = view.findViewById(R.id.iv_thumbnail)
        val description:TextView = view.findViewById(R.id.tv_description)
        val freelancerName:TextView = view.findViewById(R.id.tv_freelancer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reels, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reel = list[position]

        val mHolder = holder as ViewHolder

        Glide.with(mHolder.thumbnail)
            .load(CustomGlidePublicUrl(reel.url?:""))
            .error(ContextCompat.getDrawable(context, R.drawable.placeholder_broken_image))
            .placeholder(ContextCompat.getDrawable(context, R.drawable.light_grey_solid_corner_7))
            .into(mHolder.thumbnail)

        mHolder.description.text = reel.description?:""
        mHolder.freelancerName.text = reel.freelancer?.freelancerName?:""

        mHolder.itemView.setOnClickListener {
            callback?.onClicked()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked()
    }
}