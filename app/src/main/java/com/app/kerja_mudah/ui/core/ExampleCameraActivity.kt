package com.app.kerja_mudah.ui.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityExampleCameraBinding

class ExampleCameraActivity : BaseActivity<ActivityExampleCameraBinding>(ActivityExampleCameraBinding::inflate) {
    companion object{
        const val TYPE = "TYPE"

        const val ID_CARD_TYPE = 0
        const val SELFIE_WITH_ID_CARD_TYPE = 1
    }

    private var type:Int = ID_CARD_TYPE

    override fun initSetup() {
        initData()
        initAction()
        initView()
    }

    private fun initData() {
        type = intent.getIntExtra(TYPE, ID_CARD_TYPE)
    }

    private fun initAction() {
        binding?.btnNext?.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        binding?.ivBack?.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun initView() {
        if (type == ID_CARD_TYPE){
            binding?.llIdCard?.visibility = View.VISIBLE
            binding?.llSelfieIdCard?.visibility = View.GONE
        }else{
            binding?.llIdCard?.visibility = View.GONE
            binding?.llSelfieIdCard?.visibility = View.VISIBLE
        }
    }

    override fun inject() {

    }

}