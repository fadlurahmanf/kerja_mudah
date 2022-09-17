package com.app.kerja_mudah.ui.freelancer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.freelancer.FreelancerCategoryModel
import com.bumptech.glide.Glide

class FreelancerCategorySelectorAdapter(val list:ArrayList<FreelancerCategoryModel>, private val selected:ArrayList<FreelancerCategoryModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val ivCategory:ImageView = view.findViewById(R.id.iv_category)
        val ivSelected:ImageView = view.findViewById(R.id.iv_selected)
        val ivUnselected:ImageView = view.findViewById(R.id.iv_unselected)
        val tvCategory:TextView = view.findViewById(R.id.tv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_freelancer_category_selector, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        val mHolder = holder as ViewHolder

        Glide.with(mHolder.ivCategory).load(model.drawable)
            .into(mHolder.ivCategory)

        mHolder.tvCategory.text = model.name ?: ""


        if (selected.firstOrNull()?.name == model.name){
            mHolder.ivSelected.visibility = View.VISIBLE
            mHolder.ivUnselected.visibility = View.INVISIBLE
        }else{
            mHolder.ivSelected.visibility = View.INVISIBLE
            mHolder.ivUnselected.visibility = View.VISIBLE
        }

        mHolder.itemView.setOnClickListener {
            callBack.onClicked(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(model: FreelancerCategoryModel)
    }
}