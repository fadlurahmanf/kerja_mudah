package com.app.kerja_mudah.ui.quran

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.extension.closeKeyboard
import com.app.kerja_mudah.core.extension.hideKeyboard
import com.app.kerja_mudah.core.extension.showKeyboard
import com.app.kerja_mudah.data.response.quran.SurahResponse
import com.app.kerja_mudah.databinding.ActivitySearchSuratBinding
import com.app.kerja_mudah.ui.quran.adapter.ListSurahAdapter

class SearchSuratActivity : BaseActivity<ActivitySearchSuratBinding>(ActivitySearchSuratBinding::inflate) {

    companion object{
        const val LIST_SURAH = "LIST_SURAH"
    }

    private var list:ArrayList<SurahResponse> = arrayListOf()
    private var listSearchQuran:ArrayList<SurahResponse> = arrayListOf()

    override fun initSetup() {
        list = intent.getParcelableArrayListExtra<SurahResponse>(LIST_SURAH) ?: arrayListOf()
        this.listSearchQuran.addAll(list)

        Handler().postDelayed({
            binding?.etSearch?.requestFocus()
            binding!!.etSearch.showKeyboard()
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(binding!!.etSearch, InputMethodManager.SHOW_FORCED)
        }, 1000)
        initAdapter()
        binding?.etSearch?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((binding?.etSearch?.text?.length?:0) <= 0){
                    binding?.etSearch?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SearchSuratActivity, R.color.light_grey)))
                }else{
                    binding?.etSearch?.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SearchSuratActivity, R.color.dark_blue)))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                list.forEach {
                    println("masuk ${it.namaLatin} dan ${(it.namaLatin)?.contains(s?.toString()?.lowercase()?:"") == true}")
                }
                val newList = ArrayList(list.filter {
                    it.arti?.contains(s?.toString()?:"", false) == true
                            || it.nomor?.toString()?.contains(s?.toString()?:"", true) == true
                            || it.nama?.contains(s?.toString()?:"", true) == true
                            || it.namaLatin?.contains(s?.toString()?:"", true) == true
                            || it.tempatTurun?.contains(s?.toString()?:"", true) == true
                }.toList())
                this@SearchSuratActivity.listSearchQuran.clear()
                this@SearchSuratActivity.listSearchQuran.addAll(newList)
                adapter.setList(newList)
            }
        })
    }

    private lateinit var adapter:ListSurahAdapter
    private fun initAdapter() {
        adapter = ListSurahAdapter()
        adapter.setCallBack(object : ListSurahAdapter.CallBack{
            override fun onClicked(surahResponse: SurahResponse) {
                binding!!.etSearch.closeKeyboard()
                val intent = Intent(this@SearchSuratActivity, SurahDetailActivity::class.java)
                intent.putExtra(SurahDetailActivity.SURAH, surahResponse)
                startActivity(intent)
            }
        })
        adapter.setList(listSearchQuran)
        binding?.rv?.adapter = adapter
    }


    override fun inject() {

    }

}