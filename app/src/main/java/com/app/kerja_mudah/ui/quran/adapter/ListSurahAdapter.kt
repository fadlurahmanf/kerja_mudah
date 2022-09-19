package com.app.kerja_mudah.ui.quran.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.core.extension.changeFirstWordToUpperCase
import com.app.kerja_mudah.data.response.quran.SurahResponse

class ListSurahAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list:ArrayList<SurahResponse> = arrayListOf()
    private lateinit var callBack: CallBack

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    fun setList(list:ArrayList<SurahResponse>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val number:TextView = view.findViewById(R.id.tv_number_surah)
        val latin:TextView = view.findViewById(R.id.tv_latin_title)
        val location:TextView = view.findViewById(R.id.tv_location_surah)
        val indonesian:TextView = view.findViewById(R.id.tv_indonesian_title)
        val arabic:TextView = view.findViewById(R.id.tv_arabic_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val surah:SurahResponse = list[position]
        val mHolder = holder as ViewHolder

        mHolder.number.text = "${surah.nomor}"
        mHolder.latin.text = surah.namaLatin?:""
        mHolder.location.text = (surah.tempatTurun?:"").changeFirstWordToUpperCase()
        mHolder.arabic.text = surah.nama?:""
        mHolder.indonesian.text = surah.arti?:""
        mHolder.itemView.setOnClickListener {
            callBack.onClicked(surah)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface CallBack{
        fun onClicked(surahResponse: SurahResponse)
    }
}