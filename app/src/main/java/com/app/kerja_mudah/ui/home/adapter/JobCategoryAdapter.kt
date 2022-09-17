package com.app.kerja_mudah.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.job.JobCategoryResponse

class JobCategoryAdapter(var list:ArrayList<JobCategoryResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var tvTitle:TextView = view.findViewById(R.id.tv_title)
        var tvDefinition:TextView = view.findViewById(R.id.tv_definition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_job_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var job = list[position]

        var holder = holder as ViewHolder

        holder.tvTitle.text = job.title
        holder.tvDefinition.text = job.definition
    }

    override fun getItemCount(): Int {
        return list.size
    }
}