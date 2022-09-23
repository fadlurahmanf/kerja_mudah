package com.app.kerja_mudah.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.constant.IntentAnimationType
import com.app.kerja_mudah.data.response.core.FaqResponse
import com.app.kerja_mudah.databinding.ActivityFaqDetailBinding
import maes.tech.intentanim.CustomIntent

class FaqDetailActivity : BaseActivity<ActivityFaqDetailBinding>(ActivityFaqDetailBinding::inflate) {

    companion object{
        const val FAQ = "FAQ"
    }

    private var faq:FaqResponse ?= null

    override fun initSetup() {
        faq = intent.getParcelableExtra(FAQ)
        binding?.tvQuestion?.text = faq?.question?:""
        binding?.tvAnswer?.text = faq?.answer?:""
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, IntentAnimationType.RIGHT_TO_LEFT)
    }

    override fun inject() {

    }

}