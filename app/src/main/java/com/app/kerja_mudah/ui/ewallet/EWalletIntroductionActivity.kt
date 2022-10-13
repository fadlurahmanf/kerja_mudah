package com.app.kerja_mudah.ui.ewallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityEwalletIntroductionBinding

class EWalletIntroductionActivity : BaseActivity<ActivityEwalletIntroductionBinding>(ActivityEwalletIntroductionBinding::inflate) {
    override fun initSetup() {
        binding?.btnNext?.setOnClickListener {
            val intent = Intent(this, EWalletHomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun inject() {

    }

}