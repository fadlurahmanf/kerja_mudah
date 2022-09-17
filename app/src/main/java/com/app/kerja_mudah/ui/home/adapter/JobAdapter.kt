package com.app.kerja_mudah.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.job.JobResponse

class JobAdapter (var list: ArrayList<JobResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var job = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}