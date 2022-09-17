package com.app.kerja_mudah.ui.service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.service.ServiceOrderResponse

class ServiceOrderAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<ServiceOrderResponse> = arrayListOf()

    fun setList(list:ArrayList<ServiceOrderResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val order = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}