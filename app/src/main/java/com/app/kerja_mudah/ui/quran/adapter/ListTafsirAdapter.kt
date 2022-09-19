package com.app.kerja_mudah.ui.quran.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.data.model.quran.FontSize
import com.app.kerja_mudah.data.model.quran.QuranFontSize
import com.app.kerja_mudah.data.response.quran.AyahResponse
import com.app.kerja_mudah.data.response.quran.TafsirResponse

class ListTafsirAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<TafsirResponse> = arrayListOf()
    private var fontSize: QuranFontSize = QuranFontSize()
    private var callBack:CallBack ?= null
    private lateinit var context:Context

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun setList(list:ArrayList<TafsirResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setFontSize(fontSize: QuranFontSize){
        this.fontSize = fontSize
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
        val tafsirTitle:TextView = view.findViewById(R.id.tv_tafsir_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHolder.arabic.setTextAppearance(if (fontSize.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (fontSize.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
            mHolder.latin.setTextAppearance(if (fontSize.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.indonesian.setTextAppearance(if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.tafsir.setTextAppearance(if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.tafsirTitle.setTextAppearance(if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Bold_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Bold_12 else R.style.Font_Bold_16)
        }else{
            mHolder.arabic.setTextAppearance(context, if (fontSize.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (fontSize.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
            mHolder.latin.setTextAppearance(context, if (fontSize.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.indonesian.setTextAppearance(context, if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.tafsir.setTextAppearance(context, if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.tafsirTitle.setTextAppearance(context, if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Bold_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Bold_12 else R.style.Font_Bold_16)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onSharedClicked(tafsir:TafsirResponse)
    }
}