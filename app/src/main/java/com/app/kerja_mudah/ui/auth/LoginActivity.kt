package com.app.kerja_mudah.ui.auth

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.databinding.ActivityLoginBinding
import com.app.kerja_mudah.di.component.AuthComponent
import com.app.kerja_mudah.ui.auth.viewmodel.LoginViewModel
import com.app.kerja_mudah.ui.freelancer.SelectFreelancerCategoryActivity
import com.app.kerja_mudah.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    companion object{
        const val MESSAGE_SNACK_BAR = "MESSAGE_SNACK_BAR"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private var isPasswordVisible:Boolean = false
    override fun initSetup() {
        initData()
        isPasswordVisible = false
        binding?.etPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
        initGoogleLogin()
        initFacebookLogin()
        initAction()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initData() {
        val messageSnackBar = intent.getStringExtra(MESSAGE_SNACK_BAR)
        if (!messageSnackBar.isNullOrEmpty()){
            showSnackBar(messageSnackBar)
        }
    }

    private fun initFacebookLogin() {

    }

    private fun initGoogleLogin() {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("629896549010-mltfaf4sap3smaheon90mvc4157satnq.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)
        googleSignInClient.signOut()
    }

    private fun initObserver() {
        viewModel.loginState.observe(this){
            if (it.state == BaseState.LOADING){
                showLoadingDialog()
            }else if (it.state == BaseState.FAILED){
                dismissLoadingDialog()
                showOkDialog(
                    title = "Oops..",
                    content = it.errorMessage
                )
                googleSignInClient.signOut()
            }else if(it.state == BaseState.SUCCESS){
                dismissLoadingDialog()
                authRepository.accessToken = it.data?.data?.accessToken
                authRepository.myProfile = it.data?.data?.profile
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
//                finish()
            }
        }
    }

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var viewModel:LoginViewModel

    private fun initAction() {
        binding?.btnLogin?.setOnClickListener {
            viewModel.login(
                binding?.etEmail?.text?.toString()?:"", binding?.etPassword?.text?.toString()?:""
            )
//            val intent = Intent(this, SelectFreelancerCategoryActivity::class.java)
//            startActivity(intent)
        }

        binding?.btnGoogleLogin?.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            someActivityResultLauncher.launch(intent)
        }

        binding?.btnRegisterNow?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding?.etlPassword?.setEndIconOnClickListener {
            if (isPasswordVisible){
                binding?.etPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding?.etlPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility_off)
                isPasswordVisible = false
                binding?.etPassword?.setSelection(binding?.etPassword?.length()?:0)
            }else{
                binding?.etPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
                binding?.etlPassword?.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_visibility)
                isPasswordVisible = true
                binding?.etPassword?.setSelection(binding?.etPassword?.length()?:0)
            }
        }
    }

    private var someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val data = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            data.addOnSuccessListener { account ->
                if (account.email != null && account.id != null){
                    viewModel.loginGoogle(account.email!!, account.id!!)
                }
            }
        }
    }

    private lateinit var component:AuthComponent
    override fun inject() {
        component = appComponent.authComponent().create()
        component.inject(this)
    }

}