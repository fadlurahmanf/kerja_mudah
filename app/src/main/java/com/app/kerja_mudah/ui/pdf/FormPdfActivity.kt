package com.app.kerja_mudah.ui.pdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityFormPdfBinding

class FormPdfActivity : BaseActivity<ActivityFormPdfBinding>(ActivityFormPdfBinding::inflate) {
    override fun initSetup() {
        binding?.btnCreatePdf?.setOnClickListener {

        }
    }

    override fun inject() {

    }

}