package com.app.kerja_mudah.ui.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.databinding.ActivityBaseEmptyBinding
import com.app.kerja_mudah.di.component.CoreComponent
import com.app.kerja_mudah.ui.SplashActivity
import com.app.kerja_mudah.ui.auth.LoginActivity
import com.app.kerja_mudah.ui.core.viewmodel.BaseEmptyViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import javax.inject.Inject

class BaseEmptyActivity : BaseActivity<ActivityBaseEmptyBinding>(ActivityBaseEmptyBinding::inflate) {

    companion object{
        const val TYPE = "TYPE"
        const val BUNDLE = "BUNDLE"
    }

    private var type:String ?= null

    @Inject
    lateinit var viewModel:BaseEmptyViewModel

    override fun initSetup() {
        initData()
        observe()
    }

    private fun observe() {
        viewModel.state.observe(this){
            if (it.activateEmailStatus == BaseState.LOADING){
                showBasicLoadingDialog()
            }else if (it.activateEmailStatus == BaseState.SUCCESS){
                dismissBasicLoadingDialog()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(LoginActivity.MESSAGE_SNACK_BAR, "Account successfully activated")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }else if (it.activateEmailStatus == BaseState.FAILED){
                dismissBasicLoadingDialog()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(LoginActivity.MESSAGE_SNACK_BAR, it.errorActivateEmail)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initData() {
        type = intent.getStringExtra("TYPE")
        if (type == "CONFIRMATION-REGISTRATION"){
            val bundle = intent.getStringExtra("BUNDLE")
            if(!bundle.isNullOrEmpty()){
                val objects = Gson().fromJson(bundle, JSONObject::class.java)
                Log.e("initData", objects.optString("token"))
                if(objects.optString("token", "").isNotEmpty() || objects.optString("token_user", "").isEmpty()){
                    viewModel.activateEmail(objects.getString("token_user"), objects.getString("token"))
                }else{
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra(LoginActivity.MESSAGE_SNACK_BAR, "Something wrong with link. Please try to register again.")
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private lateinit var component: CoreComponent
    override fun inject() {
        component = appComponent.coreComponent().create()
        component.inject(this)
    }
}