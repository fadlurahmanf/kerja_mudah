package com.app.kerja_mudah.ui.quran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityQuranInformationBinding

class QuranInformationActivity : BaseActivity<ActivityQuranInformationBinding>(ActivityQuranInformationBinding::inflate) {

    override fun initSetup() {
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnNext?.setOnClickListener {
            val intent = Intent(this, ListSurahActivity::class.java)
            startActivity(intent)
        }
    }

    override fun inject() {

    }
}