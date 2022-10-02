package com.app.kerja_mudah.ui.weather.adapter

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
import com.app.kerja_mudah.core.extension.formatDate4
import com.app.kerja_mudah.core.extension.formatDate7
import com.app.kerja_mudah.data.response.weather.TimeRange
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listWeather:ArrayList<TimeRange> = arrayListOf()
    private var listTemperature:ArrayList<TimeRange> = arrayListOf()

    private lateinit var context:Context

    fun setListWeather(list:ArrayList<TimeRange>){
        this.listWeather.clear()
        this.listWeather.addAll(list)
        notifyDataSetChanged()
    }

    fun setListTemperatuer(list: ArrayList<TimeRange>){
        this.listTemperature.clear()
        this.listTemperature.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val date:TextView = view.findViewById(R.id.tv_date)
        val time:TextView = view.findViewById(R.id.tv_time)
        val sky:ImageView = view.findViewById(R.id.iv_sky)
        val celcius:TextView = view.findViewById(R.id.tv_celcius)
        val llSecond:LinearLayout = view.findViewById(R.id.ll_second)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weather = listWeather[position]
        val temperature = listTemperature.firstOrNull {
            it.datetime.equals(weather.datetime, ignoreCase = true)
        }
        val mHolder = holder as ViewHolder
        mHolder.date.text = getDate(weather.datetime?:"")
        mHolder.time.text = getTime(weather.datetime?:"")
        mHolder.sky.setImageDrawable(ContextCompat.getDrawable(context, drawableResWeather(
            weather.value?.firstOrNull {
                it.unit.equals("icon")
            }?.value?.toIntOrNull()?:0
        )))
        mHolder.celcius.text = context.getString(R.string.temperature_celcius, temperature?.value?.firstOrNull {
            it.unit.equals("C", ignoreCase = false)
        }?.value?:"")

        if (selectTimeRange(listWeather) == position){
            mHolder.llSecond.background = ContextCompat.getDrawable(context, R.drawable.solid_green_corner_5)
            mHolder.time.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)))
            mHolder.celcius.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)))
        }else{
            mHolder.llSecond.background = ContextCompat.getDrawable(context, R.drawable.solid_white_corner_5)
            mHolder.time.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_blue)))
            mHolder.celcius.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_blue)))
        }
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    private fun getDate(s: String):String{
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        return sdf.parse(s).formatDate7()?:""
    }

    private fun getTime(s:String):String{
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        return sdf.parse(s).formatDate4()?:""
    }

    private fun selectTimeRange(list:List<TimeRange>):Int{
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyyMMddHHmm")
        var index = 0
        for (i in list.indices){
            val date: Date = sdf.parse(list[i].datetime?:"") ?: continue
            if (date.after(currentDate) && i != 0){
                index = i-1
                break
            }
        }
        return index
    }

    private fun drawableResWeather(code:Int):Int{
        if (code == 0){
            return R.drawable.il_sunny
        }else if (code in 1..2){
            return R.drawable.il_partly_cloudy
        }else if (code in 3..4){
            return R.drawable.il_mostly_cloudy
        }else if (code in 5..59){
            return R.drawable.il_cloudy
        }else if (code in 60..62){
            return R.drawable.il_light_rain
        }else if (code in 63..94){
            return R.drawable.il_heavy_rain
        }else{
            return R.drawable.il_storm
        }
    }

}