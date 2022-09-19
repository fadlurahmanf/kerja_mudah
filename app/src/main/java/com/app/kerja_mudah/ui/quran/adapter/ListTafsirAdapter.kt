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
import com.app.kerja_mudah.data.response.quran.TafsirResponse

class ListTafsirAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<TafsirResponse> = arrayListOf()

    private var callBack:CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun setList(list:ArrayList<TafsirResponse>){
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
        val tafsir:TextView = view.findViewById(R.id.tv_indonesian_tafsir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tafsir, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tafsir = list[position]

        val mHolder = holder as ViewHolder

        mHolder.nomorAyah.text = tafsir.ayat?.toString() ?: ""
        mHolder.arabic.text = tafsir.ar ?: ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mHolder.latin.text = Html.fromHtml(tafsir.tr?:"", Html.FROM_HTML_MODE_COMPACT)
        }else{
            mHolder.latin.text = Html.fromHtml(tafsir.tr?:"")
        }
        mHolder.indonesian.text = tafsir.idn ?: ""
        mHolder.tafsir.text = tafsir.tafsir ?: ""
        mHolder.share.setOnClickListener {
            callBack?.onSharedClicked(tafsir)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onSharedClicked(tafsir:TafsirResponse)
    }
}