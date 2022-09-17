package com.app.kerja_mudah.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.core.extension.hideEmail
import com.app.kerja_mudah.databinding.ActivityRegistrationSuccessBinding
import com.app.kerja_mudah.di.component.AuthComponent

class RegistrationSuccessActivity : BaseActivity<ActivityRegistrationSuccessBinding>(ActivityRegistrationSuccessBinding::inflate) {
    companion object{
        const val TYPE = "TYPE"
        const val EMAIL = "EMAIL"
        const val SUCCESS = "SUCCESS"
        const val ALREADY_REGISTER_NOT_CONFIRMED = "ALREADY_REGISTER_NOT_CONFIRMED"
    }

    private lateinit var type:String

    override fun initSetup() {
        initData()
        initAction()
    }

    private fun initAction() {
        binding?.btnProceed?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initData() {
        type = intent.getStringExtra(TYPE) ?: ALREADY_REGISTER_NOT_CONFIRMED
        if (type == ALREADY_REGISTER_NOT_CONFIRMED){
            binding?.tvDescription?.text = "Your account is already registered but not confirmed, we have sent an email verification for activate your account to"
        }else{
            binding?.tvDescription?.text = "We have sent an email verification to"
        }

        binding?.tvEmail?.text = intent.extras?.getString(EMAIL, "")?.hideEmail()
    }

    private lateinit var component: AuthComponent
    override fun inject() {
        component = appComponent.authComponent().create()
        component.inject(this)
    }

}