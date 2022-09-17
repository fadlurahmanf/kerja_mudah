package com.app.kerja_mudah.ui.core

import android.os.Handler
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityPreviewMultipleImageBinding
import com.app.kerja_mudah.ui.core.adapter.PreviewImageVideoAdapter

class PreviewMultipleImageActivity : BaseActivity<ActivityPreviewMultipleImageBinding>(ActivityPreviewMultipleImageBinding::inflate) {

    companion object{
        const val LIST_MEDIA = "LIST_MEDIA"
        const val HIDE_CONFIRM_BUTTON = "HIDE_CONFIRM_BUTTON"
    }

    private var mMedias:ArrayList<String> = arrayListOf()
    private var mHideConfirmButton:Boolean = false

    override fun initSetup() {
        initData()
        refreshConfirmButton()
        initAction()
        initAdapter()
    }

    private fun initAction() {
        binding?.btnCancel?.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding?.btnSelect?.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun initData(){
        mMedias = intent.getStringArrayListExtra(LIST_MEDIA) ?: arrayListOf()
        mHideConfirmButton = intent.getBooleanExtra(HIDE_CONFIRM_BUTTON, false)

        if (mMedias.isEmpty() || mMedias.size < 2){
            binding?.tvIndex?.visibility = View.GONE
        }
    }

    private lateinit var adapter:PreviewImageVideoAdapter
    private fun initAdapter() {
        adapter = PreviewImageVideoAdapter(mMedias)
        binding?.vp?.adapter = adapter
        binding?.vp?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.tvIndex?.text = getString(R.string.index_of_total_index, position+1, mMedias.size)
                binding?.tvIndex?.visibility = View.VISIBLE
                handler.removeCallbacks(textVisibilityCallback)
                handler.postDelayed(textVisibilityCallback, 4000)
            }
        })
    }

    override fun inject() {

    }

    private fun refreshConfirmButton(){
        if (mHideConfirmButton){
            binding?.llConfirm?.visibility = View.GONE
        }else{
            binding?.llConfirm?.visibility = View.VISIBLE
        }
    }

    private val handler = Handler()

    private var textVisibilityCallback = Runnable { binding?.tvIndex?.visibility = View.GONE }

}