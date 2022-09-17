package com.app.kerja_mudah.ui.freelancer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.freelancer.FreelancerModel
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.bumptech.glide.Glide

class FreelancerPagingAdapter:PagingDataAdapter<FreelancerModel, RecyclerView.ViewHolder>(DiffUtilCallBack()) {
    private lateinit var context:Context
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val iv:ImageView = view.findViewById(R.id.iv_profile_picture)
        val freelancerHighlight:ImageView = view.findViewById(R.id.iv_highlight_freelancer)
        val tvFreelancerName:TextView = view.findViewById(R.id.tv_freelancer_name)
        val tvContent:TextView = view.findViewById(R.id.tv_content)
        val ll2:LinearLayout = view.findViewById(R.id.ll2)
        val totalReview:TextView = view.findViewById(R.id.tv_total_review)
        val overallRating:TextView = view.findViewById(R.id.tv_overall_rating)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val freelancer:FreelancerModel? = getItem(position)
        val overallRating: Double = (freelancer?.review?.totalStar?:0).toDouble() / ( if (freelancer?.review?.totalReview == null || freelancer.review?.totalReview == 0) 1 else freelancer.review?.totalReview?:1).toDouble()
        val mHolder = holder as ViewHolder

        Glide.with(mHolder.freelancerHighlight)
            .load(freelancer?.highlightPhoto?.firstOrNull())
            .centerCrop()
            .placeholder(R.drawable.ic_broken_image)
            .into(mHolder.freelancerHighlight)

        Glide.with(mHolder.iv)
            .load(freelancer?.profile?.photo?:"")
            .centerCrop()
            .placeholder(R.drawable.placeholder_person)
            .into(mHolder.iv)

        mHolder.tvFreelancerName.text = freelancer?.freelancerName ?: ""
        mHolder.tvContent.text = freelancer?.highlightText ?: ""
        mHolder.overallRating.text = String.format("%.1f", overallRating.toFloat())
        mHolder.totalReview.text = context.getString(R.string.total_review, freelancer?.review?.totalReview?:0)

        if (overallRating > 0){
            mHolder.ll2.visibility = View.VISIBLE
        }else{
            mHolder.ll2.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_freelancer_2, parent, false)
        return ViewHolder(view)
    }

    class DiffUtilCallBack:DiffUtil.ItemCallback<FreelancerModel>(){
        override fun areItemsTheSame(
            oldItem: FreelancerModel,
            newItem: FreelancerModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FreelancerModel,
            newItem: FreelancerModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }
}