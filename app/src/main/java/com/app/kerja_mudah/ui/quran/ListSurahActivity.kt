package com.app.kerja_mudah.ui.quran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.quran.SurahResponse
import com.app.kerja_mudah.databinding.ActivityListSurahBinding
import com.app.kerja_mudah.di.component.QuranComponent
import com.app.kerja_mudah.ui.quran.adapter.ListSurahAdapter
import com.app.kerja_mudah.ui.quran.viewmodel.QuranViewModel
import javax.inject.Inject

class ListSurahActivity : BaseActivity<ActivityListSurahBinding>(ActivityListSurahBinding::inflate) {
    override fun initSetup() {
        initAdapter()
        initObserver()
        viewModel.getListSurah()
    }

    private lateinit var adapter:ListSurahAdapter
    private var listSurah:ArrayList<SurahResponse> = arrayListOf()
    private fun initAdapter(){
        adapter = ListSurahAdapter()
        adapter.setCallBack(object : ListSurahAdapter.CallBack{
            override fun onClicked(surahResponse: SurahResponse) {
                val intent = Intent(this@ListSurahActivity, SurahDetailActivity::class.java)
                intent.putExtra(SurahDetailActivity.SURAH, surahResponse)
                startActivity(intent)
            }
        })
        adapter.setList(listSurah)
        binding?.rv?.adapter = adapter
        initAction()
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.ivSearch?.setOnClickListener {
            val intent = Intent(this, SearchSuratActivity::class.java)
            intent.putExtra(SearchSuratActivity.LIST_SURAH, listSurah)
            startActivity(intent)
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getListSurahState == BaseState.SUCCESS){
                listSurah.clear()
                listSurah.addAll(it.listSurah?: arrayListOf())
                adapter.setList(listSurah)

                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.VISIBLE
            }else if (it.getListSurahState == BaseState.FAILED){
                binding?.pb?.visibility = View.GONE
                binding?.tvError?.visibility = View.VISIBLE
                binding?.rv?.visibility = View.GONE
            }else if (it.getListSurahState == BaseState.LOADING){
                binding?.pb?.visibility = View.VISIBLE
                binding?.tvError?.visibility = View.GONE
                binding?.rv?.visibility = View.GONE
            }
        }
    }

    @Inject
    lateinit var viewModel:QuranViewModel

    private lateinit var component: QuranComponent
    override fun inject() {
        component = appComponent.quranComponent().create()
        component.inject(this)
    }
}