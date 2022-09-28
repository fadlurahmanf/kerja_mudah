package com.app.kerja_mudah.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.formatDate4
import com.app.kerja_mudah.core.extension.formatDate6
import com.app.kerja_mudah.core.extension.toDate
import com.app.kerja_mudah.core.extension.toRupiahFormat
import com.app.kerja_mudah.data.response.job.JobResponse
import com.bumptech.glide.Glide

class JobAdapter () : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<JobResponse> = arrayListOf()

    private var callback:CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callback = callBack
    }

    private lateinit var context:Context

    fun setList(list:ArrayList<JobResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val companyLogo:ImageView = view.findViewById(R.id.iv_partner_company_logo)
        val title:TextView = view.findViewById(R.id.tv_job_title)
        val companyName:TextView = view.findViewById(R.id.tv_company_name)
        val salary:TextView = view.findViewById(R.id.tv_total_salary)
        val workHour:TextView = view.findViewById(R.id.tv_total_hour)
        val location:TextView = view.findViewById(R.id.tv_location)
        val workTime:TextView = view.findViewById(R.id.tv_work_time)
        val jobCreated:TextView = view.findViewById(R.id.tv_job_created)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val job = list[position]

        val mHolder = holder as ViewHolder

        Glide.with(mHolder.companyLogo)
            .load(job.company?.photo?:"")
            .error(ContextCompat.getDrawable(context, R.drawable.ic_broken_image))
            .into(mHolder.companyLogo)

        mHolder.title.text = job.name?:""
        mHolder.companyName.text = job.company?.name?:""
        mHolder.salary.text = job.salary?.toInt()?.toRupiahFormat()?:""
        mHolder.workHour.text = if (job.fullDay == true) "- Full Day" else "- ${job.workHour} Hours"
        mHolder.location.text = job.city?:""
        mHolder.workTime.text = job.workTime?.toDate(true)?.formatDate4()?:""
        mHolder.jobCreated.text = job.createdAt?.toDate()?.formatDate6()

        mHolder.itemView.setOnClickListener {
            callback?.onClicked(job)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(job:JobResponse)
    }
}