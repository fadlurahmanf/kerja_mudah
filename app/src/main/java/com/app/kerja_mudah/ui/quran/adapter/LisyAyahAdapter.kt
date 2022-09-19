package com.app.kerja_mudah.ui.quran.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.response.quran.AyahResponse

class LisyAyahAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<AyahResponse> = arrayListOf()

    private var callBack:CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun setList(list:ArrayList<AyahResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val nomorAyah:TextView = view.findViewById(R.id.tv_number_ayah)
        val arabic:TextView = view.findViewById<TextView>(R.id.tv_arabic)
        val latin:TextView = view.findViewById(R.id.tv_latin)
        val indonesian:TextView = view.findViewById(R.id.tv_indonesian)
        val share:ImageView = view.findViewById(R.id.iv_share)
        val favorite:ImageView = view.findViewById(R.id.iv_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_verse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ayah = list[position]

        val mHolder = holder as ViewHolder

        mHolder.nomorAyah.text = ayah.nomor ?: ""
        mHolder.arabic.text = ayah.ar ?: ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mHolder.latin.text = Html.fromHtml(ayah.tr?:"", Html.FROM_HTML_MODE_COMPACT)
        }else{
            mHolder.latin.text = Html.fromHtml(ayah.tr?:"")
        }
        mHolder.indonesian.text = ayah.idn ?: ""
        mHolder.share.setOnClickListener {
            callBack?.onSharedClicked(ayah)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onSharedClicked(ayah:AyahResponse)
    }
}