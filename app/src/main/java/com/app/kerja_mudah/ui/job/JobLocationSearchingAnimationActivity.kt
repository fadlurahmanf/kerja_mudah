package com.app.kerja_mudah.ui.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityJobLocationSearchingAnimationBinding

class JobLocationSearchingAnimationActivity : BaseActivity<ActivityJobLocationSearchingAnimationBinding>(ActivityJobLocationSearchingAnimationBinding::inflate) {
    private var handler = Handler()

    override fun initSetup() {
        initAction()
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            finish()
        }
    }

    private var dotCount:Int = 0
    private var runnable = object : Runnable{
        override fun run() {
            var text = "Searching"
            for (i in 0 until dotCount){
                text += "."
            }
            binding?.tvSearching?.text = text
            if (dotCount >= 3){
                dotCount = 0
            }else{
                dotCount++
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun inject() {

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 0)
    }

}