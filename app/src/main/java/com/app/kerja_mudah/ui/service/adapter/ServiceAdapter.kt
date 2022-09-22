package com.app.kerja_mudah.ui.service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.toRupiahFormat
import com.app.kerja_mudah.data.response.service.ServiceDetailResponse
import com.bumptech.glide.Glide

class ServiceAdapter(var list:ArrayList<ServiceDetailResponse>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val image:ImageView = view.findViewById(R.id.iv_service)
        val description:TextView = view.findViewById(R.id.tv_description)
        val price:TextView = view.findViewById(R.id.tv_service_price)
        val title:TextView = view.findViewById(R.id.tv_service_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val serviceDetail:ServiceDetailResponse = list[position]

        val mHolder = holder as ViewHolder

        Glide.with(mHolder.image).load(serviceDetail.highlightPhoto?.first())
            .centerCrop()
            .placeholder(R.drawable.placeholder_broken_image)
            .into(mHolder.image)

        mHolder.title.text = serviceDetail.title ?: ""
        mHolder.description.text = serviceDetail.definition ?: ""
        mHolder.price.text = serviceDetail.price?.toDouble()?.toRupiahFormat()?:""

        mHolder.itemView.setOnClickListener {
            callBack.onServiceClicked(serviceDetail)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onServiceClicked(serviceDetail:ServiceDetailResponse)
    }
}