package com.app.kerja_mudah.ui.ewallet

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.toRupiahFormat
import com.app.kerja_mudah.data.response.ewallet.IncomeOutcomeResponse

class IncomeOutcomeAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context:Context

    val list:ArrayList<IncomeOutcomeResponse> = arrayListOf()

    fun setList(list:ArrayList<IncomeOutcomeResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title:TextView = view.findViewById(R.id.tv_type)
        val ivType:ImageView = view.findViewById(R.id.iv_income_outcome)
        val note:TextView = view.findViewById(R.id.tv_note)
        val total:TextView = view.findViewById(R.id.tv_total_income_outcome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_income_outcome, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val incomeOutcome = list[position]

        val mHolder = holder as ViewHolder

        mHolder.title.text = if (incomeOutcome.type == "income") "Income" else "Outcome"
        if (incomeOutcome.type == "income"){
            mHolder.ivType.setImageResource(R.drawable.ic_trending_up)
            mHolder.ivType.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue))
            mHolder.total.text = "+${incomeOutcome.total?.toInt()?.toRupiahFormat()}"
            mHolder.total.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)))
        }else{
            mHolder.ivType.setImageResource(R.drawable.ic_trending_down)
            mHolder.ivType.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            mHolder.total.text = "-${incomeOutcome.total?.toInt()?.toRupiahFormat()}"
            mHolder.total.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)))

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}