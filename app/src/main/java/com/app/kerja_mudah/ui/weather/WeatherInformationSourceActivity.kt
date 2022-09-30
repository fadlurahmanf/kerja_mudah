package com.app.kerja_mudah.ui.weather

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityWeatherInformationSourceBinding

class WeatherInformationSourceActivity : BaseActivity<ActivityWeatherInformationSourceBinding>(ActivityWeatherInformationSourceBinding::inflate) {
    override fun initSetup() {
        initAction()
    }

    private fun initAction() {
        binding?.ivBack?.setOnClickListener {
            finish()
        }

        binding?.tvLinkBmkg?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://data.bmkg.go.id/"))
            startActivity(intent)
        }

        binding?.btnNext?.setOnClickListener {

        }
    }

    override fun inject() {

    }

}