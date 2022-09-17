package com.app.kerja_mudah.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.core.AdsResponse
import com.bumptech.glide.Glide

class AdsBannerAdapter(var list:ArrayList<AdsResponse>) : RecyclerView.Adapter<AdsBannerAdapter.ViewHolder>() {

    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var imageView:ImageView = view.findViewById(R.id.iv_banner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_ads_banner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var ads = list[position]

        Glide.with(holder.imageView)
            .load(ads.thumbnail)
            .centerCrop()
            .error(R.drawable.ic_broken_image)
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            callBack.onAdsBannerClicked(ads)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onAdsBannerClicked(adsResponse: AdsResponse)
    }
}