package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide

class ImageAdapter(var list:ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callBack: CallBack

    private var isRemovedEnable:Boolean = false

    fun enableRemoved(value:Boolean){
        this.isRemovedEnable = value
    }


    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
        val ivRemove:ImageView = view.findViewById(R.id.iv_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = list[position]
        val mHolder = holder as ViewHolder

        Glide.with(mHolder.thumbnail)
            .load(image)
            .placeholder(R.drawable.light_green_corner_3)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(mHolder.thumbnail)

        mHolder.ivRemove.visibility = if (isRemovedEnable) View.VISIBLE else View.INVISIBLE

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(image)
        }

        mHolder.ivRemove.setOnClickListener {
            callBack.onRemoved(image)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(image:String)
        fun onRemoved(image: String){}
    }
}