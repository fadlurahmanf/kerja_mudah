package com.app.kerja_mudah.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {

    companion object{
        const val TITLE = "TITLE"
        const val URL = "URL"
    }

    private var url:String ?= null
    private var title:String ?= null

    override fun initSetup() {
        url = intent.getStringExtra(URL)
        title = intent.getStringExtra(TITLE)

        if (url == null){
            binding?.tvError?.text = "URL IS REQUIRED"
            binding?.webview?.visibility = View.GONE
            binding?.tvError?.visibility = View.VISIBLE
            return
        }

        binding?.tvToolbarTitle?.text = title?:""
        binding?.webview?.visibility = View.VISIBLE
        binding?.tvError?.visibility = View.GONE
        binding?.webview?.loadUrl(url?:"")
    }

    override fun inject() {

    }

}