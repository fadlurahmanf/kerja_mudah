package com.app.kerja_mudah.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.databinding.ActivityRegisterBinding
import com.app.kerja_mudah.di.component.AuthComponent
import com.app.kerja_mudah.ui.auth.viewmodel.RegisterViewModel
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate) {

    private var isPasswordVisible:Boolean = false
    private var isConfPasswordVisible:Boolean = false

    override fun initSetup() {
        initialStatePassword()
        initAction()
        initObserver()
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.registerState == BaseState.LOADING){
                showLoadingDialog()
            }else if (it.registerState == BaseState.SUCCESS){
                dismissLoadingDialog()
                val intent = Intent(this, RegistrationSuccessActivity::class.java)
                intent.putExtra(RegistrationSuccessActivity.TYPE, RegistrationSuccessActivity.SUCCESS)
                intent.putExtra(RegistrationSuccessActivity.EMAIL, binding?.etEmail?.text?.toString()?:"")
                startActivity(intent)
                finish()
            }else if (it.registerState == BaseState.FAILED){
                dismissLoadingDialog()
                if (it.errorRegister == "Email already exist, but not confirmed yet."){
                    val intent = Intent(this, RegistrationSuccessActivity::class.java)
                    intent.putExtra(RegistrationSuccessActivity.TYPE, RegistrationSuccessActivity.ALREADY_REGISTER_NOT_CONFIRMED)
                    intent.putExtra(RegistrationSuccessActivity.EMAIL, binding?.etEmail?.text)
                    startActivity(intent)
                    finish()
                    binding?.etEmail?.text?.clear()
                    binding?.etPassword?.text?.clear()
                    binding?.etConfPassword?.text?.clear()
                }else{
                    showOkDialog(
                        title = "Oops..",
                        content = it.errorRegister
                    )
                }
            }
        }
    }

    private fun initAction() {
        binding?.btnRegister?.setOnClickListener {
            if (binding?.etEmail?.text?.isEmpty() == false && Patterns.EMAIL_ADDRESS.matcher(binding?.etEmail?.text?:"").matches()
                && binding?.etFullName?.text?.isEmpty() == false
                && binding?.etPassword?.text?.isEmpty() == false
                && binding?.etConfPassword?.text?.isEmpty() == false
            ){
                viewModel.register(binding?.etEmail?.text?.toString()?:"",binding?.etFullName?.text?.toString()?:"",binding?.etPassword?.text?.toString()?:"")
            }
        }

        binding?.etlPassword?.setEndIconOnClickListener {
            refreshIconPassword()
        }

        binding?.etlConfirmPassword?.setEndIconOnClickListener {
            refreshIconConfPassword()
        }
    }

    private fun refreshIcon(){
        refreshIconPassword()
        refreshIconConfPassword()
    }

    private fun initialStatePassword(){
        isPasswordVisible = false
        binding?.etPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
        binding?.etlPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)
        binding?.etPassword?.setSelection(binding?.etPassword?.text?.length?:0)

        isConfPasswordVisible = false
        binding?.etConfPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
        binding?.etlConfirmPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)
        binding?.etConfPassword?.setSelection(binding?.etConfPassword?.text?.length?:0)
    }

    private fun refreshIconPassword(){
        if (isPasswordVisible){
            isPasswordVisible = false
            binding?.etPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
            binding?.etlPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)
            binding?.etPassword?.setSelection(binding?.etPassword?.text?.length?:0)
        }else{
            isPasswordVisible = true
            binding?.etPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding?.etlPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility)
            binding?.etPassword?.setSelection(binding?.etPassword?.text?.length?:0)
        }
    }

    private fun refreshIconConfPassword(){
        if (isConfPasswordVisible){
            isConfPasswordVisible = false
            binding?.etConfPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
            binding?.etlConfirmPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)
            binding?.etConfPassword?.setSelection(binding?.etConfPassword?.text?.length?:0)
        }else{
            isConfPasswordVisible = true
            binding?.etConfPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding?.etlConfirmPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility)
            binding?.etConfPassword?.setSelection(binding?.etConfPassword?.text?.length?:0)
        }
    }

    @Inject
    lateinit var viewModel:RegisterViewModel

    private lateinit var component: AuthComponent
    override fun inject() {
        component = appComponent.authComponent().create()
        component.inject(this)
    }

}