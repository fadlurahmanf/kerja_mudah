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

class LisyAyahAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<AyahResponse> = arrayListOf()
    private var callBack:CallBack ?= null
    private var fontSize:QuranFontSize = QuranFontSize()
    private lateinit var context:Context

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun setList(list:ArrayList<AyahResponse>){
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_verse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ayah = list[position]

        val mHolder = holder as ViewHolder

        mHolder.nomorAyah.text = ayah.nomor?.toString() ?: ""
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHolder.arabic.setTextAppearance(if (fontSize.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (fontSize.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
            mHolder.latin.setTextAppearance(if (fontSize.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.indonesian.setTextAppearance(if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }else{
            mHolder.arabic.setTextAppearance(context, if (fontSize.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (fontSize.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
            mHolder.latin.setTextAppearance(context, if (fontSize.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
            mHolder.indonesian.setTextAppearance(context, if (fontSize.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (fontSize.indonesianStyle == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onSharedClicked(ayah:AyahResponse)
    }
}