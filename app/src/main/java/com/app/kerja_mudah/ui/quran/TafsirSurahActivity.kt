package com.app.kerja_mudah.ui.quran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.repository.quran.QuranRepository
import com.app.kerja_mudah.data.response.quran.SurahResponse
import com.app.kerja_mudah.data.response.quran.TafsirResponse
import com.app.kerja_mudah.databinding.ActivityTafsirSurahBinding
import com.app.kerja_mudah.di.component.QuranComponent
import com.app.kerja_mudah.ui.quran.adapter.ListTafsirAdapter
import com.app.kerja_mudah.ui.quran.viewmodel.QuranViewModel
import javax.inject.Inject

class TafsirSurahActivity : BaseActivity<ActivityTafsirSurahBinding>(ActivityTafsirSurahBinding::inflate) {
    companion object{
        const val SURAH = "SURAH"
    }

    private var surah:SurahResponse ?= null

    override fun initSetup() {
        surah = intent.getParcelableExtra(SURAH)
        binding?.tvTafsirTitle?.text = "Tafsir ${surah?.namaLatin?:""}"
        initAction()

        if (surah == null || surah?.nomor == null){
            showSnackBar("Surah is required")
            return
        }
        initAdapter()
        initObserver()
        viewModel.getTafsir(surah?.nomor?:-1)
    }

    @Inject
    lateinit var repository: QuranRepository

    private lateinit var adapter:ListTafsirAdapter
    private var list:ArrayList<TafsirResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = ListTafsirAdapter()
        adapter.setFontSize(repository.fontSize)
        adapter.setCallBack(object : ListTafsirAdapter.CallBack{
            override fun onSharedClicked(tafsir: TafsirResponse) {

            }
        })
        adapter.setList(list)
        binding?.rv?.adapter = adapter
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getTafsirSurah == BaseState.LOADING){
                binding?.pb?.visibility = View.VISIBLE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.GONE
            }else if (it.getTafsirSurah == BaseState.SUCCESS){
                if (it.tafsirSurah != null){
                    list.clear()
                    list.addAll(mapperList(it.tafsirSurah!!))
                    adapter.setList(list)
                }
                list.forEach {
                    println("masuk ${it.ar}")
                }
                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.VISIBLE
            }else if (it.getTafsirSurah == BaseState.FAILED){
                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.VISIBLE
                binding?.rv?.visibility = View.GONE
            }
        }
    }

    private fun mapperList(surah: SurahResponse):ArrayList<TafsirResponse> {
        return ArrayList(surah.tafsir?.map { taf1 ->
            taf1.apply {
                this.ar = surah.ayat?.firstOrNull { ayat ->
                    this.ayat == ayat.nomor
                }?.ar
                this.tr = surah.ayat?.firstOrNull { ayat ->
                    this.ayat == ayat.nomor
                }?.tr
                this.idn = surah.ayat?.firstOrNull { ayat ->
                    this.ayat == ayat.nomor
                }?.idn
            }
            taf1
        }?.toList()?: arrayListOf())
    }

    @Inject
    lateinit var viewModel:QuranViewModel

    private lateinit var component: QuranComponent
    override fun inject() {
        component = appComponent.quranComponent().create()
        component.inject(this)
    }

}