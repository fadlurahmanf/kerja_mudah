package com.app.kerja_mudah.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class FreelancerAdapter(var list:ArrayList<FreelancerResponse>) : RecyclerView.Adapter<FreelancerAdapter.ViewHolder>() {

    private lateinit var callBack: CallBack
    private lateinit var context: Context

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var highlightPhoto : ImageView = view.findViewById(R.id.iv_highlight_freelancer)
        var highlightText : TextView = view.findViewById(R.id.tv_highlight)
        var tvOverAllRating : TextView = view.findViewById(R.id.tv_overall_rating)
        var tvTotalReview : TextView = view.findViewById(R.id.tv_total_review)
        var ivProfile : ImageView = view.findViewById(R.id.iv_profile_picture)
        var llReview:LinearLayout = view.findViewById(R.id.ll2)
        val freelancerName:TextView = view.findViewById(R.id.tv_freelancer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_freelancer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val freelancer = list[position]
        val overallRating: Double = (freelancer.review?.totalStar?:0).toDouble() / ( if (freelancer.review?.totalReview == null || freelancer.review?.totalReview == 0) 1 else freelancer.review?.totalReview?:1).toDouble()
        if(overallRating > 0){
            holder.llReview.visibility = View.VISIBLE
        }else{
            holder.llReview.visibility = View.GONE
        }

        val options = RequestOptions()
            .centerCrop()
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(holder.highlightPhoto)
            .load(freelancer.highlightPhoto?.firstOrNull())
            .centerCrop()
            .apply(options)
            .into(holder.highlightPhoto)

        Glide.with(holder.ivProfile)
            .load(freelancer.profile?.photo)
            .placeholder(R.drawable.placeholder_person)
            .centerCrop()
            .into(holder.ivProfile)


        holder.freelancerName.text = freelancer.freelancerName?:""
        holder.highlightText.text = freelancer.highlightText
        holder.tvOverAllRating.text = String.format("%.1f", overallRating.toFloat())
        holder.tvTotalReview.text = context.getString(R.string.total_review, freelancer.review?.totalReview?:0)

        holder.itemView.setOnClickListener {
            callBack.onFreelancerClicked(freelancer)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onFreelancerClicked(freelancer:FreelancerResponse)
    }
}