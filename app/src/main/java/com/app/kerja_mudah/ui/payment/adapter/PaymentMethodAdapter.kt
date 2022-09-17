package com.app.kerja_mudah.ui.payment.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.payment.PaymentMethodModel
import com.app.kerja_mudah.data.response.payment.PaymentMethodResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class PaymentMethodAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<PaymentMethodModel> = arrayListOf()

    var selectedMethod:PaymentMethodResponse ?= null

    private var callBack:CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun selectMethod(method: PaymentMethodResponse){
        selectedMethod = method
        notifyDataSetChanged()
    }

    fun setList(list: ArrayList<PaymentMethodModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class TitleViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title:TextView = view.findViewById(R.id.tv_text)
    }

    inner class PaymentMethodViewHolder(view: View):RecyclerView.ViewHolder(view){
        val logo:ImageView = view.findViewById(R.id.iv_logo)
        val name:TextView = view.findViewById(R.id.tv_name)
        val selected:ImageView = view.findViewById(R.id.iv_selected)
        val info:TextView = view.findViewById(R.id.tv_info)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val titleView = LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
        val methodView = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_method, parent, false)
        return if (viewType == 0){
            TitleViewHolder(titleView)
        } else {
            PaymentMethodViewHolder(methodView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder.itemViewType == 0){
            val mHolder = holder as TitleViewHolder
            mHolder.title.text = model.paymentMethodName ?: ""
        }else {
            val mHolder = holder as PaymentMethodViewHolder
            Glide.with(mHolder.logo).load(model.method?.logo?:"")
                .placeholder(R.drawable.light_grey_solid)
                .error(R.drawable.placeholder_broken_image)
                .into(mHolder.logo)
            mHolder.name.text = model.method?.name?:""

            if (model.method?.id == selectedMethod?.id){
                mHolder.selected.setImageResource(R.drawable.circle_selected)
            }else{
                mHolder.selected.setImageResource(R.drawable.circle_unselected)
            }

            mHolder.info.setOnClickListener {
                if (model.method != null){
                    callBack?.onInfoClicked(model.method!!)
                }
            }

            mHolder.itemView.setOnClickListener {
                if (model.method != null){
                    callBack?.onPaymentClicked(model.method!!)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val model = list[position]
        return if (model.paymentMethodName != null){
            0
        } else {
            1
        }
    }

    interface CallBack{
        fun onPaymentClicked(method:PaymentMethodResponse)
        fun onInfoClicked(method: PaymentMethodResponse)
    }
}