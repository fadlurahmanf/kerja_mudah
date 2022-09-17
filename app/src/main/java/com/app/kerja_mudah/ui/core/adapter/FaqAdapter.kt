package com.app.kerja_mudah.ui.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.core.FaqModel
import com.app.kerja_mudah.data.response.core.FaqResponse

class FaqAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list:ArrayList<FaqModel> = arrayListOf()

    private var callback:CallBack?= null

    fun setCallBack(callBack: CallBack){
        this.callback = callBack
    }

    fun setList(list:ArrayList<FaqModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class FaqViewHolder(view:View):RecyclerView.ViewHolder(view){
        val question:TextView = view.findViewById(R.id.tv_question)
    }

    inner class CategoryFaqViewHolder(view: View):RecyclerView.ViewHolder(view){
        val category:TextView = view.findViewById(R.id.tv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val faqView = LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false)
        val categoryView = LayoutInflater.from(parent.context).inflate(R.layout.item_faq_category, parent, false)
        return if (viewType == 0){
            CategoryFaqViewHolder(categoryView)
        } else {
            FaqViewHolder(faqView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val faq = list[position]
        if (holder.itemViewType == 0){
            val mHolder = holder as CategoryFaqViewHolder
            mHolder.category.text = faq.faqCategory?:""
        }else{
            val mHolder = holder as FaqViewHolder
            mHolder.question.text = faq.faqResponse?.question?:""
            if (faq.faqResponse != null){
                mHolder.itemView.setOnClickListener {
                    callback?.onClicked(faq.faqResponse!!)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].faqResponse == null){
            0
        } else {
            1
        }
    }

    interface CallBack{
        fun onClicked(faq:FaqResponse)
    }
}