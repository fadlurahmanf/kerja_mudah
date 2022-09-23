package com.app.kerja_mudah.ui.freelancer.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.formatDate
import com.app.kerja_mudah.core.extension.formatDate2
import com.app.kerja_mudah.core.extension.isoDateTimeToDate
import com.app.kerja_mudah.core.extension.toDate
import com.app.kerja_mudah.data.model.review.ReviewDetailModel
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class FreelancerReviewAdapter : RecyclerView.Adapter<FreelancerReviewAdapter.ViewHolder>() {
    private var list:ArrayList<ReviewDetailModel> = arrayListOf()
    private lateinit var context:Context
    private var layout:Int = R.layout.item_review

    fun setList(list:ArrayList<ReviewDetailModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setLayout(layout:Int){
        this.layout = layout
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var commenterProfilePicture:ImageView = view.findViewById(R.id.iv_commenter_profile_picture)
        var commenterName:TextView = view.findViewById(R.id.tv_commenter_name)
        var llCommenterStar:LinearLayout = view.findViewById(R.id.ll_commenter_star)
        var commentTime:TextView = view.findViewById(R.id.tv_comment_time)
        var tvComment:TextView = view.findViewById(R.id.tv_comment)
        var llFreelancerComment:LinearLayout = view.findViewById(R.id.ll_freelancer_comment)
        var freelancerProfilePicture:ImageView = view.findViewById(R.id.iv_freelancer_profile_picture)
        var freelancerName:TextView = view.findViewById(R.id.tv_freelancer_name)
        var freelancerComment:TextView = view.findViewById(R.id.tv_comment_freelancer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = list[position]

        Glide.with(holder.commenterProfilePicture)
            .load(review.from?.photo)
            .centerCrop()
            .placeholder(R.drawable.placeholder_person)
            .into(holder.commenterProfilePicture)

        holder.commenterName.text = review.from?.fullName?:""

        val listStar = arrayOfNulls<ImageView>(review.star?:0)
        holder.llCommenterStar.removeAllViews()
        for (i in listStar.indices){
            val mStarView = ImageView(context)
            val lp = LinearLayout.LayoutParams(40, 40)
            mStarView.layoutParams = lp
            mStarView.setImageResource(R.drawable.ic_star)
            mStarView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellow))
            listStar[i] = mStarView
        }

        for (i in listStar.indices){
            holder.llCommenterStar.addView(listStar[i])
        }

        holder.commentTime.text = review.createdAt?.toDate()?.formatDate2()?:"null"
        holder.tvComment.text = review.comment?:"Great"
        holder.llFreelancerComment.visibility = View.GONE
        if (review.freelancerComment != null){
            holder.llFreelancerComment.visibility = View.VISIBLE
            Glide.with(holder.freelancerProfilePicture)
                .load(review.freelancerPhoto?:"")
                .centerCrop()
                .placeholder(R.drawable.placeholder_person)
                .into(holder.freelancerProfilePicture)
            holder.freelancerName.text = review.freelancerName
            holder.freelancerComment.text = review.freelancerComment
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}