package com.app.kerja_mudah.ui.freelancer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.formatDate
import com.app.kerja_mudah.core.extension.isoDateTimeToDate
import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class FreelancerReviewAdapter(var list:ArrayList<ReviewDetailModel>, var layout:Int = R.layout.item_review) : RecyclerView.Adapter<FreelancerReviewAdapter.ViewHolder>() {
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var profilePicture:ImageView = view.findViewById(R.id.iv_profile_picture)
        var tvFullName:TextView = view.findViewById(R.id.tv_full_name)
        var tvComment:TextView = view.findViewById(R.id.tv_comment)
        var tvStar:TextView = view.findViewById(R.id.tv_overall_rating)
        var tvCreatedAt:TextView = view.findViewById(R.id.tv_created_at)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = list[position]
        holder.tvFullName.text = review.from?.email
        holder.tvComment.text = review.comment?:"Great!"
        holder.tvStar.text = review.star?.toDouble()?.toString()
        Glide.with(holder.profilePicture)
            .load(review.from?.photo)
            .centerCrop()
            .placeholder(R.drawable.placeholder_person)
            .into(holder.profilePicture)
        holder.tvCreatedAt.text = review.createdAt?.isoDateTimeToDate()?.formatDate()

    }

    override fun getItemCount(): Int {
        return list.size
    }
}