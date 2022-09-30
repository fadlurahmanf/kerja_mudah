package com.app.kerja_mudah.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.weather.Area

class WeatherCityAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<Area> = arrayListOf()

    private var callback:CallBack ?= null

    fun setCallback(callBack: CallBack){
        this.callback = callBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val cityName:TextView = view.findViewById(R.id.tv_city_name)
    }

    fun setList(list:ArrayList<Area>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val area = list[position]
        val mHolder = holder as ViewHolder
        mHolder.cityName.text = area.city
        mHolder.itemView.setOnClickListener {
            callback?.onClicked(area)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(area: Area)
    }
}